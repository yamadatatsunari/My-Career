# 衣類提案システム(Item Finder)
衣服や骨格を撮影し、似合う服装を提案する

## 使用言語
- python
- HTML
- CSS

## 使用ツール
- VisualStudioCode
- SQLite

## app.py
- このファイルを実行することでサーバーが立ち上がる
- DBの接続と各コントローラーへのルーティングを行なっている

## controllers
- 画面を表示するための準備をする
- 処理をしたり画面の表示に必要な変数を定義する
- ざっくり機能単位でコントローラーを用意する
  - トップ画面や紹介画面を担当する`homes_controller`
  - 服の撮影画面や解析を担当する`clothes_controller`

## models
- データ構造を定義する

## templates
- HTMLを格納する

## static
- CSSやJSのファイルを格納する

## config.py
- 諸々の設定
