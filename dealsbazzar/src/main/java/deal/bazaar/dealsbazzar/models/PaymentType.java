package deal.bazaar.dealsbazzar.models;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@AllArgsConstructor
public class PaymentType {
    @Id
    private String paymentTypeId;
    private String paymentType;
    
    
    @Override
    public String toString() {
        return "PaymentType [paymentType=" + paymentType + ", paymentTypeId=" + paymentTypeId + "]";
    }


}
