(ns test-clojure.clojure-xml
  (:use clojure.test)
  (:require [clojure.xml :as xml])
  (:import [java.io ByteArrayInputStream]))

(defn str-contains?
  [big little]
  (not= -1 (.indexOf big little)))

(defn xml->str [x]
  (with-out-str
    (xml/emit-element x)))

(defn str->xml [s]
  (xml/parse
   (ByteArrayInputStream. (.getBytes s "UTF-8"))))

(deftest emit-should-escape-attribute-content
  (testing "http://www.assembla.com/spaces/clojure/tickets/408"
    (are [literal escaped]
         (let [xml (xml->str {:tag :e :attrs {:a literal} :content nil})]
           (is (str-contains? xml escaped)))
         "&" "&amp;"
         "<" "&lt;"
         ">" "&gt;")))

(deftest emit-should-escape-element-content
  (testing "http://www.assembla.com/spaces/clojure/tickets/408"
    (are [literal escaped]
         (let [xml (xml->str {:tag :e :attrs nil :content [literal]})]
           (is (str-contains? xml escaped)))
         "&" "&amp;"
         "<" "&lt;"
         ">" "&gt;")))

(deftest quote-in-attribute-content-should-not-break-xml
  (testing "http://www.assembla.com/spaces/clojure/tickets/408"
    (are [c]
         (let [x {:tag :e :attrs {:a c} :content nil}]
           (= x (-> x xml->str str->xml)))
         "'"
         "\"")))

(deftest parse-and-emit-should-round-trip-data
  (testing "http://www.assembla.com/spaces/clojure/tickets/410"
    (are [s]
         (let [x1 (str->xml s)
               x2 (str->xml (xml->str x1))]
           (is (= x1 x2)))
         "<e/>"
         "<e></e>"
         "<e>content</e>"
         "<e a='attribute'/>"
         "<e a='attribute'>content</e>"
         "<e><e><e/></e></e>"
         "<e> </e>"
         "<e>&lt;&amp;&gt;</e>")))


