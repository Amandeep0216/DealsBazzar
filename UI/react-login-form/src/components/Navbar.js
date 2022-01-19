import React from 'react';
import { Button } from './Button';
import { Link, Navigate } from 'react-router-dom';
import './Navbar.css';
import {connect} from 'react-redux'
import store from '../Redux/Store';
import { ACTION_USER_LOGOUT } from ".././Redux/Action/UserAction";

var mapStateToProps = state => {
  return {
     user: state.user,
  }
}

class Navbar extends React.Component
{
  constructor(){
    super()
    this.state = {
       
        loginstatus:false
       
    }       
}

componentDidMount()
{
   
    
    console.log(this.props.user)
 
    
}

logout = (event)=>{
  this.setState({loginstatus:true}) 
  store.dispatch({...ACTION_USER_LOGOUT})
} 
render(){
  if(this.state.loginstatus){
    return(
    <Navigate to={"/"}/> )
}

   return(

    <>
      <nav className='navbar'>
        <div className='navbar-container'>
          <Link to='/' className='navbar-logo' >
           DealsBazaar
            <i class='fab fa-typo3' />
          </Link>
          <div className='menu-icon' >
            <i className={'fas fa-times', 'fas fa-bars'} />
          </div>
          <ul className={ 'nav-menu active' , 'nav-menu'}>
            <li className='nav-item'>
           {this.props.user.loginstatus==true?   <Link to='/dashboard' className='nav-links' >
                Home
              </Link>:
              <Link to='/' className='nav-links' >
              Home
            </Link>}
            </li>
        
            <li className='nav-item'>
            {this.props.user.loginstatus==true?       <Link
                to='/myproducts' className='nav-links'>
               product
              </Link>:
               <Link
               to='/' className='nav-links'>
              product
             </Link>}
            </li>
       

      
            
          </ul>
       {this.props.user.loginstatus==false?  <Button buttonStyle='btn--outline'>SIGN UP</Button>:
       <Button buttonStyle='btn--outline' onClick={this.logout}>Logout</Button>}
        </div>
      </nav>
    </>
  
  );
}
}


export default connect(mapStateToProps,null)(Navbar)