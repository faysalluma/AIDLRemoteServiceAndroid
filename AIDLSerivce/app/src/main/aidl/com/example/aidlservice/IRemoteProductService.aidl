// IRemoteProductService.aidl
package com.example.aidlservice;

// Declare any non-default types here with import statements
import com.example.aidlservice.models.Product;

interface IRemoteProductService {
   void addProduct(String name , int quantity, float cost);
   Product getProduct(String name);
}