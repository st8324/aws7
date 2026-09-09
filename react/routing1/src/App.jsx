import {BrowserRouter} from "react-router-dom";
import { Footer, Header } from "./Layout";
import Main from "./Main";

function App() {
  return (
    <BrowserRouter>
      <Header/>
      <Main/>
      <Footer/>
    </BrowserRouter>
  );
}

export default App;
