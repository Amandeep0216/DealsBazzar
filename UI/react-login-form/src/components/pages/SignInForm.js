import React from "react";
import { Link, Redirect, NavLink, Navigate  } from "react-router-dom";
import "./main.css"
import Store from '../../Redux/Store'
import * as action from "../../Redux/Action/TokenAction"
import Navbar from "../Navbar";
import { ACTION_USER_LOGIN } from "../../Redux/Action/UserAction";
import {
  GoogleLoginButton,
  FacebookLoginButton
} from "react-social-login-buttons";

export default class SignInForm extends React.Component {
  constructor() {
    super();

    this.state = {
      email: "",
      password: "",
      loginStatus:false
    }
  }

  componentDidUpdate(){
    console.log("After Mounting: ",this.props.token)
  }

    login = (event)=>{
      var ob = {
       
          email:this.email.value,
          password: this.password.value,
         
      }
  
     /*  function login(){

        let history = useHistory();
     
      } */

      fetch(`http://localhost:8080/web/login`,{
        method : 'POST',
        headers:{
            "Content-Type" : "application/json"
        },
        body : JSON.stringify(ob)
    }).then(response=>response.json()).then(data=>{
      console.log(data);
       // var token=data.token
        if (data.status){
          Store.dispatch({...ACTION_USER_LOGIN,payload:{
            token:data.token
         
          }

          })
          this.setState({loginStatus:true})
        }
        
        this.setState({regmsg:data.msg})
    });;
  
      console.log(ob)
      event.preventDefault()
  } 

  
  

  render() {
    if(this.state.loginStatus){
      return <Navigate to="/dashboard"></Navigate>
    }
    return (

      <>
      <Navbar />
      <div className="App">
        <div className="appAside" />
        <div className="appForm">

          <div className="pageSwitcher">
            <Link
              to="/"
              activeClassName="pageSwitcherItem-active"
              className="pageSwitcherItem"
            >
              Sign In
            </Link>
            <Link
              exact
              to="/sign-up"
              activeClassName="pageSwitcherItem-active"
              className="pageSwitcherItem"
            >
              Sign Up
            </Link>
          </div>



          <div className="formCenter">
            <form className="formFields" onSubmit={this.login}>
              <div className="formField">
                <label className="formFieldLabel" htmlFor="email">
                  E-Mail Address
                </label>
                <input
                  type="email"
                  ref={c => this.email = c}
                  id="email"
                  className="formFieldInput"
                  placeholder="Enter your email"
                  name="email" />
              </div>

              <div className="formField">
                <label className="formFieldLabel" htmlFor="password">
                  Password
                </label>
                <input
                  type="password"
                  ref={c => this.password = c}
                  id="password"
                  className="formFieldInput"
                  placeholder="Enter your password"
                  name="password" />
              </div>

              <div className="formField">
                <button type="submit" name="login" class="login" value="login" className="formFieldButton">Sign In</button>{" "}
                <Link to="/" className="formFieldLink">
                  Create an account
                </Link>
              </div>

              <div className="socialMediaButtons">
                <div className="googleButton">
                  <GoogleLoginButton onClick={() => alert("Login")} />
                </div>

                <div className="instagramButton">
                  <FacebookLoginButton onClick={() => alert("Login")} />

                </div>
              </div>
            </form>
          </div>
        </div>
      </div>
      </>
    );
    }
}

//export default connect(mapStatetpProps)(SignInForm);