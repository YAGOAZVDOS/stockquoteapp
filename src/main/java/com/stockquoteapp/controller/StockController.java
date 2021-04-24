package com.stockquoteapp.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.stockquoteapp.model.Stock;
import com.stockquoteapp.repository.StockRepository;

@RestController
public class StockController {

	@Autowired
	private StockRepository stockRepository; 
	
	@PostMapping(value = "/stock")
	@ResponseStatus(value = HttpStatus.CREATED)
	public Stock createStock(@Validated @RequestBody Stock stock) {
		
		return stockRepository.save(stock);
	}
	
	@PatchMapping(value = "/stock/{name}")
	public ResponseEntity<Stock> updateStock(@PathVariable("name") String name, @RequestBody Stock stockPatch) {
		Optional<Stock> stock = stockRepository.findById(name);
		if(stock.isPresent()) {
			Stock stockUpdate = stock.get();
				
			ArrayList<String> quotes = stock.get().getQuotes();
			for (String string : stockPatch.getQuotes()) {
				quotes.add(string);
			}
			
			stockUpdate.setQuotes(quotes);
			
			return new ResponseEntity<Stock>(stockRepository.save(stockUpdate), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/stock")
	public List<Stock> readAll(){
		return stockRepository.findAll();
		
	}
	
	@GetMapping(value = "/stock", params = "name")
	public ResponseEntity<Stock> readByName(@RequestParam(required = true, value = "name") String name){
		Optional<Stock> stock = stockRepository.findById(name);
		if(stock.isPresent()) {
			return new ResponseEntity<Stock>(stock.get(), HttpStatus.FOUND);
		}else {
			return new ResponseEntity(stock, HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping(value =  "/stock/{name}")
	public ResponseEntity<Stock> delete(@PathVariable("name") String name){
		Optional<Stock> stock = stockRepository.findById(name);
		if (stock.isPresent()) {
			stockRepository.delete(stock.get());
			return new ResponseEntity( HttpStatus.OK);
		}else {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		 
	}
	
}	
	