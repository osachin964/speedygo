import "./App.css";
import React, { useState, useEffect } from "react";
import MainDash from "./components/MainDash/MainDash";
import RightSide from "./components/RigtSide/RightSide";
import Sidebar from "./components/Sidebar";
import LogIn from "./components/Login/Login";
import TrackOrder from "./components/OrderTracker/TrackOrder";
import "semantic-ui-css/semantic.min.css";
import TrackBookingPage from "./components/MainDash/TrackBookingPage";
import AboutUsPage from "./components/MainDash/AboutUs";
import ContactUs from "./components/MainDash/ContactUs";

function App() {
  const [selectedFeature, setselectedFeature] = useState("Dashboard");
  const [isLoggedIn, setisLoggedIn] = useState(
    JSON.parse(localStorage.getItem("user")) ? true : false
  );

  const [selectedTransporter, setselectedTransporter] = useState(null);

  useEffect(() => {
    window.addEventListener("beforeunload", clearLocalStorage);

    return () => {
      window.removeEventListener("beforeunload", clearLocalStorage);
    };
  }, []);
  const clearLocalStorage = () => {};

  const loggedInUser = JSON.parse(localStorage.getItem("user"));

  return isLoggedIn ? (
    <div className="App">
      <div id="AppGlassId" className="AppGlass">
        <Sidebar
          role={loggedInUser.role}
          logIn={setisLoggedIn}
          setselectedTransporter={setselectedTransporter}
          setselectedFeature={setselectedFeature}
        />
        {selectedFeature === "Dashboard" ? (
          <>
            {" "}
            <MainDash
              role={loggedInUser.role}
              setselectedTransporter={setselectedTransporter}
            />
            {loggedInUser.role === "Customer" ? (
              <RightSide selectedTransporter={selectedTransporter} />
            ) : (
              ""
            )}
          </>
        ) : (
          ""
        )}
        {selectedFeature === "Track Bookings" ? (
          <TrackBookingPage
            role={loggedInUser.role}
            setselectedFeature={setselectedFeature}
          />
        ) : (
          ""
        )}
        {selectedFeature === "About us" ? (
          <AboutUsPage setselectedFeature={setselectedFeature} />
        ) : (
          ""
        )}
        {selectedFeature === "Contact us" ? <ContactUs setselectedFeature={setselectedFeature} /> : ""}
      </div>
    </div>
  ) : (
    <LogIn logIn={setisLoggedIn} />
  );
}

export default App;
