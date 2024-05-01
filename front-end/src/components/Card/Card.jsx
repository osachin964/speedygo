import React, { useEffect, useState } from "react";
import "./Card.css";
import { CircularProgressbar } from "react-circular-progressbar";
import "react-circular-progressbar/dist/styles.css";
import { motion, AnimateSharedLayout } from "framer-motion";
import { UilTimes } from "@iconscout/react-unicons";
import Chart from "react-apexcharts";
import Checkout from "../CheckoutComp/Checkout";

// parent Card

const Card = (props) => {

  const [expanded, setExpanded] = useState(false);
  return (
    <AnimateSharedLayout>
      {expanded ? (
        <ExpandedCard
          param={props}
          setExpanded={() => {
            document
              .getElementById("AppGlassId")
              .classList.remove("disable-background");
            localStorage.setItem("customer", null);
            setExpanded(false);
          }}
        />
      ) : (
        <CompactCard param={props} setExpanded={() => setExpanded(true)} />
      )}
    </AnimateSharedLayout>
  );
};

// Compact Card
function CompactCard({ param, setExpanded }) {
  const Png = param.png;
  return (
    <motion.div
      className="CompactCard"
      style={{
        background: param.color.backGround,
        boxShadow: param.color.boxShadow,
      }}
      layoutId="expandableCard"
      onClick={setExpanded}
    >
      <div className="radialBar">
        <CircularProgressbar
          value={param.barValue}
          text={`${(param.barValue / 20).toFixed(2)}`}
        />
        <span>Since {param.title} years</span>
      </div>
      <div className="detail">
        <Png />
        <span>{param.value}</span>
        <span>{param.officeAddress}</span>
      </div>
    </motion.div>
  );
}

// Expanded Card
function ExpandedCard({ param, setExpanded }) {
  const [height, setheigh] = useState(100);
  const [width, setwidth] = useState(100);

  useEffect(() => {
    let div = document.querySelector(".ExpandedCard");
    setwidth(div.offsetWidth / 2);
    setheigh(div.offsetHeight / 2);
    param.changeTsp(param.transporterId)
    localStorage.setItem("selectedTransporter",param.transporterId)
  }, []);

  return (
    <motion.div
      className="ExpandedCard"
      style={{
        background: param.color.backGround,
        boxShadow: param.color.boxShadow,
        marginLeft: -width,
        marginRight: -height,
      }}
      layoutId="expandableCard"
    >
      <div style={{ alignSelf: "flex-end", cursor: "pointer", color: "white" }}>
        <UilTimes onClick={setExpanded} />
      </div>

      <Checkout tspId={param.transporterId} />
    </motion.div>
  );
}

export default Card;
