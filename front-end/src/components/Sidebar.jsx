import React, { useState } from "react";
import "./Sidebar.css";
import Logo from "../imgs/logo.png";
import { UilSignOutAlt } from "@iconscout/react-unicons";
import { SidebarDataForCustomer, SidebarDataForTsp } from "../Data/Data";
import { UilBars } from "@iconscout/react-unicons";
import { motion } from "framer-motion";

const Sidebar = (props) => {
  const [selected, setSelected] = useState(0);

  const [expanded, setExpaned] = useState(true);
  const SideBarData =
    props.role === "Customer" ? SidebarDataForCustomer : SidebarDataForTsp;

  const sidebarVariants = {
    true: {
      left: "0",
    },
    false: {
      left: "-60%",
    },
  };
  let logOutUser = () => {
    props.setselectedTransporter(null);
    localStorage.setItem("user", null);
    props.setselectedFeature("Dashboard");
    localStorage.clear();
    props.logIn(false);
  };
  return (
    <>
      <div
        className="bars"
        style={expanded ? { left: "60%" } : { left: "5%" }}
        onClick={() => setExpaned(!expanded)}
      >
        <UilBars />
      </div>
      <motion.div
        className="sidebar"
        variants={sidebarVariants}
        animate={window.innerWidth <= 768 ? `${expanded}` : ""}
      >
        <div className="logo">
          <img src={Logo} alt="logo" />
          <span>
            Speedy<span>GO</span>
          </span>
        </div>

        <div className="menu">
          {SideBarData.map((item, index) => {
            return (
              <div
                className={selected === index ? "menuItem active" : "menuItem"}
                key={index}
                onClick={() => {
                  setSelected(index);
                  props.setselectedFeature(item.heading)
                }}
              >
                <item.icon />
                <span>{item.heading}</span>
              </div>
            );
          })}
          {/* signoutIcon */}
          <div className="menuItem">
            <UilSignOutAlt onClick={logOutUser} />
          </div>
        </div>
      </motion.div>
    </>
  );
};

export default Sidebar;
