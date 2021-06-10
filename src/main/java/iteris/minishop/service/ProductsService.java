package iteris.minishop.service;

import iteris.minishop.domain.dto.ProductCreateRequest;
import iteris.minishop.domain.dto.ProductResponse;
import iteris.minishop.domain.entity.ImportRegistry;
import iteris.minishop.domain.entity.Product;
import iteris.minishop.domain.entity.Supplier;
import iteris.minishop.exception.ProductHasOrdersException;
import iteris.minishop.exception.ProductNotFoundException;
import iteris.minishop.exception.SupplierNotFoundException;
import iteris.minishop.exception.isDiscontinuedInvalidException;
import iteris.minishop.repository.ImportRegistryRepository;
import iteris.minishop.repository.ProductsRepository;
import iteris.minishop.repository.SuppliersRepository;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductsService {
    private final ProductsRepository productsRepository;
    private final SuppliersRepository suppliersRepository;
    private final ImportRegistryRepository importRegistryRepository;


    @Autowired
    public ProductsService(ProductsRepository productsRepository, SuppliersRepository suppliersRepository, ImportRegistryRepository importRegistryRepository){
        this.productsRepository = productsRepository;
        this.suppliersRepository = suppliersRepository;
        this.importRegistryRepository = importRegistryRepository;
    }

    public ProductResponse cadastrarProduto(ProductCreateRequest produto){
        var idSupplier = produto.getIdSupplier();

        var supplierEncontrado = suppliersRepository.findById(idSupplier);

        if(supplierEncontrado.isEmpty()){
            throw new SupplierNotFoundException("Supplier: " + idSupplier + " não encontrado");
        }

        var novoProduto = new Product();
        novoProduto.setProductName(produto.getProductName());
        novoProduto.setDiscontinued(produto.getIsDiscontinued());
        novoProduto.setPackageName(produto.getPackageName());
        novoProduto.setUnitPrice(produto.getUnitPrice());
        novoProduto.setSupplier(supplierEncontrado.get());

        var produtoSalvo = productsRepository.save(novoProduto);

        return new ProductResponse(
                produtoSalvo.getIdProduct(),
                produtoSalvo.getProductName(),
                produtoSalvo.getUnitPrice(),
                produtoSalvo.getPackageName(),
                produtoSalvo.isDiscontinued(),
                produtoSalvo.getSupplier()
        );
    }

    public List<ProductResponse> listarTodos(){
        var resultado = productsRepository.findAll();

        var listaDeProdutos = resultado.stream().map(product -> {
            return new ProductResponse(
                    product.getIdProduct(),
                    product.getProductName(),
                    product.getUnitPrice(),
                    product.getPackageName(),
                    product.isDiscontinued(),
                    product.getSupplier()
            );
        }).collect(Collectors.toList());

        return listaDeProdutos;
    }

    public ProductResponse procurarProduto(Integer id){
        var retorno = productsRepository.findById(id);

        if(retorno.isEmpty()){
            throw new ProductNotFoundException("Produto: " + id + " não encontrado");
        }

        var produto = retorno.get();

        return new ProductResponse(
                produto.getIdProduct(),
                produto.getProductName(),
                produto.getUnitPrice(),
                produto.getPackageName(),
                produto.isDiscontinued(),
                produto.getSupplier()
        );
    }

    public ProductResponse deletarProduto(Integer id){
        var retorno = productsRepository.findById(id);

        if(retorno.isEmpty()){
            throw new ProductNotFoundException("Produto: " + id + " não encontrado");
        }

        var produto = retorno.get();

        if(!produto.getOrderItems().isEmpty()){
            throw new ProductHasOrdersException("Produto: " + id + " tem pedidos registrados, sendo assim não pode ser apagado.");
        }

        productsRepository.delete(produto);


        return new ProductResponse(
                produto.getIdProduct(),
                produto.getProductName(),
                produto.getUnitPrice(),
                produto.getPackageName(),
                produto.isDiscontinued(),
                produto.getSupplier()
        );
    }
    public ImportRegistry importarProdutos(MultipartFile file) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);
        var auxiliarDeuCerto = 0;
        var auxiliarDeuErrado = 0;

        for(int i=1;i<worksheet.getPhysicalNumberOfRows() ;i++) {
            XSSFRow row = worksheet.getRow(i);

            var supplier = pegarSupplier((int) row.getCell(1).getNumericCellValue());

            if(supplier != null) {
                Product produto = new Product();
                produto.setProductName(row.getCell(0).getStringCellValue());
                produto.setSupplier(supplier);
                produto.setUnitPrice(row.getCell(2).getNumericCellValue());
                produto.setPackageName(row.getCell(3).getStringCellValue());
                produto.setDiscontinued(converterParaBooleano((int)row.getCell(4).getNumericCellValue()));
                productsRepository.save(produto);
                auxiliarDeuCerto++;
            }else {
                auxiliarDeuErrado ++;
            }

        }

        LocalDateTime lt = LocalDateTime.now();
        String nomeDoArquivo = file.getOriginalFilename();

        var registro =  new ImportRegistry();
        registro.setQtdSucesso(auxiliarDeuCerto);
        registro.setQtdFalha(auxiliarDeuErrado);
        registro.setDataReg(lt);
        registro.setNomeDoArquivo(nomeDoArquivo);

        importRegistryRepository.save(registro);

        return registro;
    }

    public Supplier pegarSupplier(Integer idSupplier){
        Supplier supplierEncontrado = null;
        var retorno = suppliersRepository.findById(idSupplier);

        if(retorno.isPresent()){
            supplierEncontrado = retorno.get();
        }
        return supplierEncontrado;
    }

    public Boolean converterParaBooleano(Integer bool){
        Boolean auxiliar = null;

        switch(bool){
            case 0:
                auxiliar = false;
                break;
            case 1:
                auxiliar = true;
                break;
        }
        return auxiliar;
    }
}
