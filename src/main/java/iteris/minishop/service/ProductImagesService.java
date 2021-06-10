package iteris.minishop.service;

import iteris.minishop.domain.dto.ProductImageUpdatedResponse;
import iteris.minishop.domain.dto.ProductImagesResponse;
import iteris.minishop.domain.entity.Product;
import iteris.minishop.domain.entity.ProductImage;
import iteris.minishop.exception.ProductImagesNotFoundException;
import iteris.minishop.exception.ProductImagesUploadException;
import iteris.minishop.exception.ProductNotFoundException;
import iteris.minishop.exception.SequenceInvalidException;
import iteris.minishop.repository.ProductImagesRepository;
import iteris.minishop.repository.ProductsRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductImagesService {
    private final ProductImagesRepository productImagesRepository;
    private final ProductsRepository productsRepository;

    public ProductImagesService(ProductImagesRepository productImagesRepository, ProductsRepository productsRepository){
        this.productImagesRepository = productImagesRepository;
        this.productsRepository = productsRepository;
    }

    public ProductImagesResponse uploadFile(MultipartFile arquivo, Integer idProduto, Integer idSequencia){
        if(idSequencia != 1 && idSequencia != 2 && idSequencia != 3){
            throw new SequenceInvalidException("Sua sequência deve ser entre 1 e 3.");
        }

        var produto = productsRepository.findById(idProduto);
        var produtoSalvo = produto.get();

        File path = new File("C:\\projects\\Imagens\\" + arquivo.getOriginalFilename());

        String caminhoDoArquivo = path.toString();

        var procurarImagem = productImagesRepository.procurarImagem(caminhoDoArquivo, idProduto);

        if(produto.isEmpty()){
            throw new ProductNotFoundException("Produto: " + idProduto + " não encontrado");
        }

        if(!procurarImagem.isEmpty()){
            throw new ProductImagesUploadException("Imagem já associada a este produto.");
        }

        var listaDeImagens = productImagesRepository.listarImagensPorProduto(idProduto);

        if(listaDeImagens.isPresent()){
            var listaDeImagensEncontrada = listaDeImagens.get();

            var contadorImagens = (long) listaDeImagensEncontrada.size();

            if(contadorImagens >= 3){
                throw new ProductImagesUploadException("Seu produto só pode possuir 3 imagens.");
            }
        }

        var listaDeIdSequencia = produtoSalvo.getProductImages().stream().map(ProductImage::getSequencia).collect(Collectors.toList());

        if(listaDeIdSequencia.contains(idSequencia)){
            throw new ProductImagesUploadException("Essa sequência de imagem já existe para o produto associado.");
        }

        try {
                path.createNewFile();

                FileOutputStream output = new FileOutputStream(path);
                output.write(arquivo.getBytes());
                output.close();

                var imagem = new ProductImage();
                imagem.setPath(caminhoDoArquivo);
                imagem.setSequencia(idSequencia);
                imagem.setProduct(produtoSalvo);

                var imagemSalva = productImagesRepository.save(imagem);

                return new ProductImagesResponse(
                        imagemSalva.getIdProductImage(),
                        imagemSalva.getPath(),
                        imagemSalva.getSequencia(),
                        imagemSalva.getProduct()
                );

        } catch (Exception e){
            throw new ProductImagesUploadException("Upload da imagem não foi realizado com sucesso");
        }

    }

    public List<ProductImagesResponse> listar(){
        var retorno = productImagesRepository.findAll();

        var listaDeImagens = retorno.stream().map(imagem ->{
            return new ProductImagesResponse(
                    imagem.getIdProductImage(),
                    imagem.getPath(),
                    imagem.getSequencia(),
                    imagem.getProduct()
            );
        }).collect(Collectors.toList());

        return listaDeImagens;
    }

    public ProductImageUpdatedResponse alterarPosicao(Integer idImg, Integer idSequencia){

        if(idSequencia != 1 && idSequencia != 2 && idSequencia != 3){
            throw new SequenceInvalidException("Sua sequência deve ser entre 1 e 3.");
        }

        var procurarImagem = productImagesRepository.findById(idImg);

        if(procurarImagem.isEmpty()){
            throw new ProductImagesNotFoundException("Imagem com o ID: " + idImg + " não encontrada.");
        }

        var imagemEncontrada = procurarImagem.get();

        //SALVA A SEQUENCIA QUE ESSA VAI IMAGEM PARTIR
        var sequenciaAtual = imagemEncontrada.getSequencia();

        var produto = imagemEncontrada.getProduct();
        var produtoId = produto.getIdProduct();

        var imagemDestino = productImagesRepository.imagemPorProduto(produtoId, idSequencia);

        if(imagemDestino.isPresent()) {

            var imagemDestinoEncontrada = imagemDestino.get();

            imagemEncontrada.setSequencia(idSequencia);
            imagemDestinoEncontrada.setSequencia(sequenciaAtual);
            var imagem = productImagesRepository.save(imagemEncontrada);
            var imagem2 = productImagesRepository.save(imagemDestinoEncontrada);

            return new ProductImageUpdatedResponse(
                    imagem.getIdProductImage(),
                    imagem.getPath(),
                    imagem.getSequencia(),
                    imagem.getProduct(),

                    imagem2.getIdProductImage(),
                    imagem2.getPath(),
                    imagem2.getSequencia(),
                    imagem2.getProduct()
            );
        }

        imagemEncontrada.setSequencia(idSequencia);
        var imagemSalva = productImagesRepository.save(imagemEncontrada);

        return new ProductImageUpdatedResponse(
                imagemSalva.getIdProductImage(),
                imagemSalva.getPath(),
                imagemSalva.getSequencia(),
                imagemSalva.getProduct(),

                 null,
                null,
                null,
                null
        );
    }
}
