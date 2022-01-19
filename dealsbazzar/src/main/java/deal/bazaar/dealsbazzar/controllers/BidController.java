package deal.bazaar.dealsbazzar.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import deal.bazaar.dealsbazzar.models.Bid;
import deal.bazaar.dealsbazzar.responses.ResponseData;
import deal.bazaar.dealsbazzar.services.BidService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bid")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:3000" })
public class BidController {
    

    Date date=new Date();
    SimpleDateFormat formatter=new SimpleDateFormat("dd/mm/yyyy");
    String currentDate=formatter.format(date);

    @Autowired
    private BidService bidService;

    @PostMapping("/addBid")
    public ResponseData addBId(@RequestBody Bid bid) {
        bid.setBidId(UUID.randomUUID().toString());
        bid.setBidDate(currentDate);
        bid.setBidStatus(-1);
        Bid data = bidService.addBid(bid);
        if (data != null) {

            return new ResponseData(200, data, "bid placed successfully");
        }
        return new ResponseData(400, null, "bid is not placed");
    }

    @GetMapping("/getBids")
    public ResponseData getBids() {

        List<Bid> bidList = bidService.getBids();
        if (bidList.size() > 0) {
            return new ResponseData(200, bidList, "fetched");
        }
        return new ResponseData(400, null, "no bids placed");
    }

    @PutMapping("/updateBid")
    public ResponseData updateBid(@RequestBody Bid bid){
        Bid data = bidService.validateId(bid.getBidId());
        if (data == null) {
            return new ResponseData(800, null, "bid not found");
        }
        data=new Bid();
        data=bidService.updateBid(bid);
        if(data==null){
            return new ResponseData(400, bid, "bid could not be updated");
        }else{
            return new ResponseData(200, data, "bid is successfully updated");
        }
    }

    @DeleteMapping("/deleteBid/{bidId}")
    public ResponseData deleteBid(@PathVariable String bidId) {
        Bid b = bidService.validateId(bidId);
        if (b == null) {
            return new ResponseData(800, null, "bid not found");
        }
        boolean result = bidService.deleteBid(bidId);
        if (result) {
            return new ResponseData(200, null, "successfully deleted");
        } else {
            return new ResponseData(400, null, "bid could not deleted");
        }
    }

}