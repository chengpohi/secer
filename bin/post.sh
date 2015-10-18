#!/bin/bash

function zhihu(){
  curl -v --cookie "USER_TOKEN=Yes" -XPOST "localhost:9000/crawler" -d '{ 
	"url": "http://www.zhihu.com/question/20666092", 
	"indexName": "www", 
	"indexType": "zhihu", 
	"selectors": [ {"field": "title", "selector": "div#zh-question-title"} ], 
	"urlRegex": "http://www.zhihu.com/question/\\d+"
  }'
}

function stackoverflow(){
  curl -XPOST "localhost:9000/crawler" -d '{ 
	"url": "http://stackoverflow.com/questions/2039904/what-statistics-should-a-programmer-or-computer-scientist-know", 
	"indexName": "www", 
	"indexType": "stackoverflow", 
	"selectors": [{"field": "_title", "selector": "title"},  {"field": "_question", "selector": "div.post-text"}, {"field": "_answers", "selector": "div#answers"} ], 
	"urlRegex": "http://stackoverflow.com/questions/\\d+/.*" 
  }'
}

function printDetail(){
    echo "--------------------------------"
    echo "1. Zhihu Crawler"
    echo "2. Stackoverflow Crawler"
    echo "0. Exit"
    echo "Input:"
}

while true; do
    printDetail
    read f
    case $f  in
	"1" )
	    zhihu ;;
	"2" )
	    stackoverflow ;;
	"3" )
	    login ;;
	"0" )
	    exit ;;
    esac
done


