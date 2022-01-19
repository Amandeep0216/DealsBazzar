import {combineReducers, createStore} from 'redux'
import ProductReducer from './Reducer/ProductReducer'
import ProductIdReducer from './Reducer/ProductIdReducer'
import UserReducer from './Reducer/UserReducer'

var store=createStore(combineReducers({
    products:ProductReducer,
    product:ProductIdReducer,
    user : UserReducer
}),{
    products:[],
    product:{},
    user : { loginstatus : false, token : undefined , username : undefined}
})

export default store;