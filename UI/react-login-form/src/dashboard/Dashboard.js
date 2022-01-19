import React from 'react'
import './Dashboard.css'
import { connect } from 'react-redux'
import Store from '../Redux/Store'

import * as actions from '../Redux/Action/ProductIdAction'
import { Link } from 'react-router-dom'
import Navbar from '../components/Navbar'

var mapStateToProps = state => {
  return {
    products: state.products,
    product: state.product
  }
}

class Dashboard extends React.Component {

  /* constructor(){
    super()
    this.state = {
        logoinstatus:false,
    }
  } */
/* 
  logout = (event)=>{
    this.setState({logoinstatus:true}) 
    Store.dispatch({...ACTION_USER_LOGOUT})
  } 
 */
  setPid = (product) => {
    Store.dispatch({
      ...actions.ACTION_SET_PRODUCT_ID, payload: {
        product: product
      }
    })
  }

  render() {
    return <>
     <Navbar />
      <div className="container">
        <div className="row row-cols-2 row-cols-lg-4 g-2 g-lg-3">

          {this.props.products.map((product, index) => {
            return <>
              <div className="col">
                <div className="p-3">
                  <div className="card">
                    <div className="card-body">
                      <img src={`data:image/jpeg;base64,${product.productImages[0]}`} className="card-img-top" alt="first product" />
                      <h5 className="card-title">{product.productName}</h5>
                      <p className="card-text"><strong>Description : </strong> {product.productDescription}</p>
                    </div>
                    <div className="list-group list-group-flush">
                      <h6 className="list-group-item"><strong>price : &#8377; </strong>{product.vendorPrice}</h6>
                      <h6 className="list-group-item"><strong>Measurement : </strong>{product.productMeasuremntType}</h6>
                      <h6 className="list-group-item"><strong>Stocks Available : </strong>{product.productStock}</h6>
                    </div>
                    <div className="card-body">
                      <Link to="/mybid" ><button className="btn btn-warning" onClick={() => this.setPid(product)}>bid</button></Link>
                    </div>
                  </div>
                </div>
              </div>
            </>
          })}
        </div>
      </div>
    </>
  }
}

export default connect(mapStateToProps)(Dashboard)