// html上で指定したidを取得する
const btn = document.getElementById('btn');
const btntable1 = document.getElementById('table1');
const btntable2 = document.getElementById('table2');
// const btntext1 = document.getElementById('text1');
const btntext2 = document.getElementById('text2');
const btntext3 = document.getElementById('text3');
const btntext4 = document.getElementById('text4');
const btntext5 = document.getElementById('text5');
const btntext6 = document.getElementById('text6');
const btntext7 = document.getElementById('text7');
const linetext1 = document.getElementById('line1');


// 「確認」ボタンクリックでhiddenクラスを付け外しする
// 「確認」ボタンクリックにより，表示/非表示の切り替え動作を実装
btn.addEventListener('click', () => {
  btntable1.classList.toggle('hidden');
  btntable2.classList.toggle('hidden');
//   btntext1.classList.toggle('hidden');
  btntext2.classList.toggle('hidden');
  btntext3.classList.toggle('hidden');
  btntext4.classList.toggle('hidden');
  btntext5.classList.toggle('hidden');
  btntext6.classList.toggle('hidden');
  btntext7.classList.toggle('hidden');
  linetext1.classList.toggle('hidden');
});

//htmlから送られたデータの確認
//データの行数分実行
// document.write("JavaScriptのプログラムコードに引き渡された変数チェック<br>");
// for(let i = 0; i < global_values.length ; i++) {
//     document.write(global_values[i] + "<br>");
// }

function averageOfData(data) {
  //温湿度の合計値，データ数の初期化
  let sum_tempe = 0.0;
  let sum_humid = 0.0;
  let number_of_data = 0;

  for(let i = 0 ; i < data.length ; i++) {
    //htmlから送られたデータをvalueに格納
    let value = data[i];
    
    //Debug
    // console.log(value);
    // console.log(typeof(value));

    //Numberを用いてobject型を数値に変換
    sum_tempe += Number(value[0]);
    
    //Debug
    //console.log(sum_tempe);

    //Numberを用いてobject型を数値に変換
    sum_humid += Number(value[1]);

    //データ数をカウント
    number_of_data = number_of_data + 1;
  }

  //温湿度の平均を求める
  const average_tempe = sum_tempe / number_of_data;
  const average_humid = sum_humid / number_of_data;

  //.toFixedを用いて小数点以下の表示桁数指定(()の中で桁数指定)
  return [average_tempe.toFixed(1), average_humid.toFixed(1), number_of_data];
}

let average_data_for_dht11 = averageOfData(global_values_for_dht11)
//htmlにデータを渡すために，idを設定する
document.getElementById('ave_tempe_dht11').textContent = average_data_for_dht11[0];
document.getElementById('ave_humid_dht11').textContent = average_data_for_dht11[1];
document.getElementById('number_of_data_dht11').textContent = average_data_for_dht11[2];

let average_data_for_bme280 = averageOfData(global_values_for_bme280)
document.getElementById('ave_tempe_bme280').textContent = average_data_for_bme280[0];
document.getElementById('ave_humid_bme280').textContent = average_data_for_bme280[1];
document.getElementById('number_of_data_bme280').textContent = average_data_for_bme280[2];

//テーブルの追加処理
function appendToTable() {
  var $formObject = document.getElementById( "Form" );
  var $tableObject = document.getElementById( "Table" );
  var $tr = "<tr>";
  $tr += "<td>" + $formObject.formTempe.value + "</td>";
  $tr += "<td>" + $formObject.formHumid.value + "</td>";
  $tr += "</tr>";
  $tableObject.insertAdjacentHTML( "beforeend", $tr );
}

// alert("確認するダイアログ") ;
