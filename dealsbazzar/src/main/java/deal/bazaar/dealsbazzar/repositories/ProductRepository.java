package deal.bazaar.dealsbazzar.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import deal.bazaar.dealsbazzar.models.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product,String> {
    
}
