class BidService{
    addBidService=(data)=>{
        console.log("service: ",data)
        return fetch("http://localhost:8080/bid/addBid", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        })
    }
}

var obj = new BidService()
export default obj;