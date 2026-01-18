# AuditLogMap

#### Coding challenge: Creating a custom, thread-safe,  collection class in Java ####

As a Java developer, you must have used a number of collections. 

A few examples:

## Arrays 
## ArrayLists
## LinkedLists  
## Sets 
## Maps 

There are third party libraries like Google Guava, and FastUtil that provide their own collections with additional functionality. Please explore them. 

Your goal is to create a new class called  AuditLogMultiMap that provides these functionalities:

## Keys can contain zero, 1 or multiple values  ( A key cannot contain duplicate values) 
## The collection cannot contain duplicate keys 
## Methods to add or remove values for a specific key
## Method to remove a key altogether 
## Methods to export the collection in JSON format 
## Method to retrieve keys that have no values 
## Maintain a complete history of actions performed with respect to any key 
## Maintain the number of values for all keys 
## Maintain the total number of keys 
## Be threadsafe (Multiple threads can perform operations on an instance of AuditLogMultiMap)
