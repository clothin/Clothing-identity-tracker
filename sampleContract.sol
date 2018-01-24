pragma solidity ^0.4.0;

import "github.com/Arachnid/solidity-stringutils/strings.sol";

contract ClothingTrackingV6 {
    
    // external lib for concatenating strings
    using strings for *;
      
    // factory structure
    struct factory {
        string country;     // country code
        string factoryName; // factory name
        string workDone;    // work with product description
        string tip;        // tip 
        string mapsLoc;     // lokacija string
        uint score;      // certScore
    }

    // product structure
    struct product {
        string productName; // product name
        string size;        // product size (M, L, XXL or 44, 46...)
        string material;    // made of materials as string
        string color;       // color
        string price;         // *1000
        uint numFact;       // number of factory flows (internal use)
        string picURL;      // url to picture
        factory[] factoryFlow;  // factory flow as added to array
    }
    
    mapping(uint256 => product) productList; // list of products mapped to unique id
    uint256[] productArray;                 // array of all products

    mapping(uint256 => factory) factoryList; // list of all factories mapped to unique id
    factory[] factoryArray;                 // list of all factories

    // Add new factory (id, name, country code, work)
    function addFactory(uint256 _id, string _country, string _name, string _tip, string _loc, uint256 _score) public {        
        var itemnew = factory(_country, _name, "", _tip, _loc, _score);
        factoryList[_id] = itemnew;
        factoryArray.push(itemnew);
    }

    // return #factories
    function countFactories() public constant returns (uint count)  {     
        return factoryArray.length;
    }

    // rmeove factory
    function removeFactory(uint256 code) public {
        delete factoryList[code];
    }

    // get info about factory (id)
    function getFactory(uint256 _id) public constant returns (string val1, string val2, uint256 val3, string val4) {   
        return (factoryList[_id].factoryName, factoryList[_id].country, factoryList[_id].score, factoryList[_id].tip);
    }
    
    // add new product (id, name, size, material (string)
    function addProduct(uint256 _id, string _name, string _size, string _material, string _color, string _price, string _pic) public {
        
        productList[_id].productName = _name;
        productList[_id].size = _size;
        productList[_id].material = _material;
        productList[_id].color = _color;
        productList[_id].price = _price;
        productList[_id].picURL = _pic;
        productList[_id].numFact = 0;
        
        productArray.push(_id);
    }
    
    // add new factory to flow of a product (productID, factoryID, workDoneInFactory)
    function addFactoryToProduct(uint256 _productID, uint256 _factoryID, string _work) public {
        factory memory f = factoryList[_factoryID];
        product storage p = productList[_productID];
        
        f.workDone = _work;
        p.factoryFlow.push(f);
        p.numFact++;
    }
    
    // Get info about product
    function getProductInfo(uint256 _id) public constant returns (string name, string size, string material, string color, string price, string url) {   
        factory[] memory facts = productList[_id].factoryFlow;
        // string memory s1 = "lsls";
        string memory s = "";
        
        return (productList[_id].productName,productList[_id].size,productList[_id].material,productList[_id].color,productList[_id].price,productList[_id].picURL);
    }
    
    function getProductFlowInfo(uint256 _id) public constant returns (string flowData, uint score) {
         factory[] memory facts = productList[_id].factoryFlow;
         uint accScore = 0;
        // string memory s1 = "lsls";
        string memory s = "";
        for (uint i = 0; i < productList[_id].numFact; i++) {
           // factData.push(facts[i].country);
           string memory s1 = facts[i].country;
           string memory s2 = facts[i].factoryName;
           string memory s3 = facts[i].workDone;
           string memory s4 = facts[i].tip;
           string memory s5 = facts[i].mapsLoc;
           s = s.toSlice().concat(s1.toSlice());
           s = s.toSlice().concat("#".toSlice());
           s = s.toSlice().concat(s2.toSlice());
           s = s.toSlice().concat("#".toSlice());
           s = s.toSlice().concat(s3.toSlice());
           s = s.toSlice().concat("#".toSlice());
           s = s.toSlice().concat(s4.toSlice());
           s = s.toSlice().concat("#".toSlice());
           s = s.toSlice().concat(s5.toSlice());
           s = s.toSlice().concat(";".toSlice());
           accScore += facts[i].score;
        }
        
        return (s, accScore);
    }
    
}
