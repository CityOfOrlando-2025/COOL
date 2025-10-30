import "./App.css";
import LandingPage from "./pages/LandingPage";
import SignInPage from "./pages/SignInPage";
import DevicePage from "./pages/DeviceAvailabilityPage";
import NearestPage from "./pages/NearestCenterPage";
import { Routes, Route } from "react-router-dom";

function App() {
  return (
    <>
      <Routes>
        <Route path="/" element={<LandingPage />} />
        <Route path="/signIn" element={<SignInPage />} />
        <Route path="/device" element={<DevicePage />} />
        <Route path="/nearest" element={<NearestPage />} />
      </Routes>
    </>
  );
}

export default App;
