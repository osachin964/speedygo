import React from "react";
import CustomerReview from "../CustomerReview/CustomerReview";
import Updates from "../Updates/Updates";
import "./RightSide.css";

const RightSide = (props) => {
  return (
    <div className="RightSide">
      <div className="reviewsContainer">
        <h3>Customer Reviews</h3>
        <Updates selectedTsp={props.selectedTransporter}/>
      </div>
      {/* <div>
        <h3>Sale Forecast</h3>
        <CustomerReview />
      </div> */}
    </div>
  );
};

export default RightSide;
