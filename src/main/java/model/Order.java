package model;

import lombok.Data;

import java.util.HashMap;

@Data
public class Order {
    public HashMap<String, Integer> orderInfo = new HashMap<>();
}
