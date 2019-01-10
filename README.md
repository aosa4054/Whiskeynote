<div style="text-align: canter;"><img src="https://github.com/aosa4054/Whiskeynote/blob/master/img/logo.png" alt="whiskeynote"></div>

## 概要
開発中のAndroidアプリ、**Whiskeynote**のソースコードです。  
ウイスキー初心者が感想などを簡単にメモすることができるようになればと思い開発しています。  
  
  
## 作成中のUI
開発中の画面です。

<img src="https://github.com/aosa4054/Whiskeynote/blob/master/img/%E3%83%A1%E3%82%A4%E3%83%B3%E7%94%BB%E9%9D%A2.png" width="200">   <img src="https://github.com/aosa4054/Whiskeynote/blob/master/img/%E7%99%BB%E9%8C%B2%E7%94%BB%E9%9D%A2.png" width="200">    
  
それぞれトップ画面とウイスキーをメモする画面です。
  
**カラーや一部のUIについてはこのドキュメント作成時の段階で、すでに上のスクリーンショットとは異なっています。**（UIが整い次第更新します。）

## 仕様
* MVVMを採用し、メモされたウイスキーのデータをViewModelで取得してViewに表示するシンプルな作りになっています。
* 外部との通信機能は実装されておらず、ローカルで完結します。
  
#### 使用ライブラリなど
* Android Architecture Components
    * DataBinding
    * Room 
    * ViewModel
    * LiveData
    * Navigation
* Material Components for Android
* Koin
* PermissionsDispatche

## ライセンスについて
このアプリは [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0 ) のライセンスで配布されている成果物を含んでいます。
  
基本的には通常の著作権法に従います。詳しい内容については[こちら](https://choosealicense.com/no-permission/ "No License")をご覧ください。
