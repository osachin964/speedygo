import React, { useState } from "react";
import { Step } from "semantic-ui-react";

const TrackOrder = (props) => {
  return props.activeStep == 10 ? (
    <Step.Group ordered>
      <Step completed>
        <Step.Content>
          <Step.Title>Order Placed</Step.Title>
          <Step.Description>Your order has been placed</Step.Description>
        </Step.Content>
      </Step>

      <Step>
        {" "}
        {/* <Icon name="delete" /> */}
        <Step.Content>
          <Step.Title>Order Declined</Step.Title>
          <Step.Description>Your order is rejected</Step.Description>
        </Step.Content>
      </Step>
    </Step.Group>
  ) : (
    <Step.Group ordered>
      <Step
        completed={props.activeStep > 0}
        active={props.activeStep === 0}
        disabled={props.activeStep < 0}
      >
        <Step.Content>
          <Step.Title>Order Placed</Step.Title>
          <Step.Description>
            Your Order had been placed succesfully
          </Step.Description>
        </Step.Content>
      </Step>

      <Step
        completed={props.activeStep > 1}
        active={props.activeStep === 1}
        disabled={props.activeStep < 1}
      >
        <Step.Content>
          <Step.Title>Order Accepted</Step.Title>
          {props.activeStep > 1 ? (
            <Step.Description>
              Your order has been accepted by the transporter
            </Step.Description>
          ) : (
            <Step.Description>
              Waiting for order to be accepted by transporter
            </Step.Description>
          )}
        </Step.Content>
      </Step>
      <Step
        completed={props.activeStep > 2}
        active={props.activeStep === 2}
        disabled={props.activeStep < 2}
      >
        <Step.Content>
          <Step.Title>In Transit</Step.Title>
          {props.activeStep > 2 ? (
            <Step.Description>
              Your order has been Shipped.
            </Step.Description>
          ) : (
            <Step.Description>
              Waiting for transporter to ship your order.
            </Step.Description>
          )}
        </Step.Content>
      </Step>
      <Step
        completed={props.activeStep > 3}
        active={props.activeStep === 3}
        disabled={props.activeStep < 3}
      >
        <Step.Content>
          <Step.Title>Delivered</Step.Title>
          {props.activeStep > 3 ? (
            <Step.Description>
              Your order has been delivered successfully.
            </Step.Description>
          ) : (
            <Step.Description>
              Waiting for order to be delivered.
            </Step.Description>
          )}
        </Step.Content>
      </Step>
    </Step.Group>
  );
};

export default TrackOrder;
