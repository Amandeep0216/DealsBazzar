import React from 'react'
import { connect } from 'react-redux'
import './MyBid.css'
import BidService from '../Service/BidService'

var mapStateToProps = state => {
  return {
    product: state.product
  }
}

class MyBid extends React.Component {


  Bid = (event) => {
    var ob = {
      productId: this.props.product.productId,
      bidPrice: this.bidderPrice.value,
      bidStock: this.bidderStock.value
    }
    if (!(ob.bidPrice.length == 0 || ob.bidStock.length == 0)) {
        BidService.addBidService(ob)
        .then(response=>response.json())
        .then(data=>{
          if(data.statusCode==200){
            alert("bid placed is successfully")
          }else{
            alert("bid not placed")
          }
        })
    }
    else {
      alert("please provide the price and stock for the product")
      console.log("no operations performed")
    }
    event.preventDefault();
  }

  render() {
    return <>
      <div className="container">
        <div className="row row-cols-2 row-cols-lg-3 g-2 g-lg-3">
          {this.props.product.productImages.map((image) => {
            return <div className="col">
              <div className="image-border p-3 border bg-light">
                <img className="w-100 h-100" src={`data:image/jpeg;base64,${image}`} />
              </div>
            </div>
          })}
        </div>
      </div>

      <div className="list-group list-group-flush">
        <h6 className="font-weight-bold">{this.props.product.productName}</h6>
        <h6 ><strong>Description : </strong> {this.props.product.productDescription}</h6>
        <h6 ><strong>price : &#8377; </strong> {this.props.product.vendorPrice}</h6>
        <h6 ><strong>Measurement : </strong> {this.props.product.productMeasuremntType}</h6>
        <h6 ><strong>Stocks Available : </strong> {this.props.product.productStock}</h6>
      </div>

      <hr /><hr />

      <form onSubmit={this.Bid}>
        <div className="input-group mb-3">
          <div className="form-group">
            <label >Set Price</label>
            <input type="number" className="form-control" ref={c => this.bidderPrice = c} id="exampleInputPassword1" min={this.props.product.vendorPrice+500} placeholder="price" />
          </div>
          &ensp;
          <div className="form-group">
            <label >Number of Stocks</label>
            <input type="number" className="form-control" ref={c => this.bidderStock = c} min="10" id="exampleInputPassword1" max={this.props.product.productStock} placeholder="stock" />
          </div>
          &ensp;
          <button type="submit" className="btn btn-primary">Bid</button>
        </div>
      </form>
    </>
  }
}
export default connect(mapStateToProps)(MyBid);