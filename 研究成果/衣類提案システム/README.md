# Item Finder
衣服や骨格を撮影し、似合う服装を提案する

## app.py
- このファイルを実行することでサーバーが立ち上がる
- DBの接続と各コントローラーへのルーティングを行なっている

> [!TIP]
> ルーティングとは　URLが送られてきたときに、どのファイルを実行するか定義したもの<br>
> （例）URLが"/"のときにhomes_controllerを動かすよ！！
> ```py
>  app.register_blueprint(homes_controller, url_prefix="/")
>```

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
