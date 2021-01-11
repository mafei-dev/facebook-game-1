package com.game._1.model;

import lombok.Data;

import java.util.List;

/*
  @Author kalhara@bowsin
  @Created 1/11/2021 5:15 AM  
*/
@Data
public class Database {
    private List<GenderFile> male;
    private List<GenderFile> female;
}
