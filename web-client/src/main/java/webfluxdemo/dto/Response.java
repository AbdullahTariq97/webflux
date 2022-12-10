package webfluxdemo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@ToString
public class Response {
    private Date data = new Date();
    private int output;

    public Response(int output){
        this.output = output;
    }
}
