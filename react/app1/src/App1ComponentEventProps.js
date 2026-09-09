
//컴포넌트 생성 및 이벤트 등록, props 설명 예제
/*
컴포넌트 특징
- 컴포넌트는 함수 또는 클래스로 선언
- 컴포넌트는 대문자로 시작
- 반환하는 태그는 하나의 태그로 감싸야 함
- return ()를 붙임
- export default 컴포넌트; // 1개의 컴포넌트만 내보냄=> 다른곳에서 쓸수 있음
- export {컴포넌트1, 컴포넌트2, ..}; //여러개의 컴포넌트를 내보냄
*/
function App1ComponentEventProps() {

  //함수 표현식
  const clickEvent = ()=> alert(1);

  //함수 선언식
  function clickEvent2(){
    alert(2);
  }

  return (
    <div>
      <button onClick={clickEvent}>버튼1</button>
      <button onClick={clickEvent2}>버튼2</button>
      <Button1/>
      <Button2/>
      <Button click={()=>alert(5)} text={"버튼5"}/>
      <Button 
        click={()=>alert(6)} 
        text={"버튼6"} 
        style={{
          color : "white", 
          backgroundColor : "red"
        }} 
      />
    </div>
  );
}

//Button1 컴포넌트
function Button1(){
  return (
    <button>버튼3</button>
  );
}
//Butoon2 컴포넌트
const Button2 = () =>{
  return (
    <button>버튼4</button>
  );
}
//props를 이용하여 부모 컴포넌트에서 보낸 값들을 활용할 수 있음
function Button({text, click, style}){
  return (
    <button onClick={click} style={style}>{text}</button>
  )
}

export default App1ComponentEventProps;
