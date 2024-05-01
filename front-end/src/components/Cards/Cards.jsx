import React from "react";
import "./Cards.css";
import { cardsData } from "../../Data/Data";

import Card from "../Card/Card";

const Cards = (props) => {
  return (
    <div className="Cards">
      {props.cardsData.map((card, id) => {
        return (
          <div className="parentContainer" key={id}>
            <Card
              title={card.title}
              color={card.color}
              barValue={card.barValue}
              value={card.value}
              png={card.png}
              transporterId={card.transporterId}
              officeAddress={card.officeAddress}
              changeTsp={props.changeTsp}
            />
          </div>
        );
      })}
    </div>
  );
};

export default Cards;
