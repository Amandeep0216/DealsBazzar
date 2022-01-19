package deal.bazaar.dealsbazzar.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import deal.bazaar.dealsbazzar.models.Bid;

@Repository
public interface BidRepository extends MongoRepository<Bid,String> {
    
}
