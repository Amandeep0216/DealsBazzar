import * as actiontype from '../Action/Action'

export default function ProductReducer(state=[],action){
    switch(action.type){
        case actiontype.ADD_PRODUCTS:return [...state,action.payload.product]
        case actiontype.LOAD_PRODUCTS:return action.payload.products
        case actiontype.UPDATE_PRODUCTS:return state.map(prod=>{
           if(prod.productId==action.payload.pid){
                prod.productImages=action.payload.images
                prod.productStatus=action.payload.status
                return prod
            }
            else
            {
                return prod
            }
        })
        default: return state
    }
}