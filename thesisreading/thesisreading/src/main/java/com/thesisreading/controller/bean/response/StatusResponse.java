package com.thesisreading.controller.bean.response;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatusResponse {
    @Expose
    String code;
    @Expose
    String err;

    // success
    public static StatusResponse ok(){
        return StatusResponse.builder().code("1").build();
    }

    //error
    public static StatusResponse error(String code, String msg){
        return StatusResponse.builder().code(code).err(msg).build();
    }
}

