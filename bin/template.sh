#!/bin/bash

function putTemplate(){
    curl -XPUT "localhost:9200/_template/crawler?pretty" -d '
	{
		"template" : "www",
			"settings" : {
				"number_of_shards" : 1
			},
			"mappings" : {
                                "_default_": {
                                            "properties": {
                                                "md5": {
                                                    "type": "string",
                                                    "index": "not_analyzed"
                                                },
                                                "created_at": {
                                                  "type": "date",
                                                  "format": "dateOptionalTime"
                                                },
                                                "url": {
                                                  "type": "string",
                                                   "analyzer": "simple"
                                                }
                                             }
                                     }
			}
	}
	'
}

function getTemplate(){
    curl -XGET "localhost:9200/_template/crawler?pretty"
}

function deleteTemplate(){
    curl -XDELETE "localhost:9200/_template/crawler"
}

function createIndex(){
    curl -XPUT "localhost:9200/secersearch"   
}

function deleteIndex(){
    echo "delete Index Name"
    read indexName
    curl -XDELETE "localhost:9200/$indexName"   
}

function getIndexMapping(){
    echo "Input Index Name"
    read indexName
    curl -XGET "localhost:9200/$indexName/_mapping?pretty"   
}

function count() {
    curl -XGET "localhost:9200/_count?pretty"
}

function deleteAll() {
    curl -XDELETE "localhost:9200/*"
}

function printDetail(){
    echo "--------------------------------"
    echo "1. Get Template"
    echo "2. PUT Template"
    echo "3. Delete Template"
    echo "4. Create Index"
    echo "5. Get Index Mapping"
    echo "6. Delete Index"
    echo "7. Count Index"
    echo "8. Delete All"
    echo "0. Exit"
    echo "Input:"
}

while true; do
    printDetail
    read f
    case $f  in
	"1" )
	    getTemplate ;;
	"2" )
	    putTemplate ;;
	"3" )
	    deleteTemplate ;;
	"4" )
	    createIndex ;;
	"5" )
	    getIndexMapping ;;
	"6" )
	    deleteIndex ;;
	"7" )
	    count ;;
	"8" )
	    deleteAll ;;
	"0" )
	    exit ;;
    esac
done
