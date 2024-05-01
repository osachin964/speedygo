import React, { useState, useRef, useEffect } from "react";
import CssBaseline from "@mui/material/CssBaseline";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Container from "@mui/material/Container";
import Toolbar from "@mui/material/Toolbar";
import Paper from "@mui/material/Paper";
import Stepper from "@mui/material/Stepper";
import Step from "@mui/material/Step";
import StepLabel from "@mui/material/StepLabel";
import Button from "@mui/material/Button";
import Link from "@mui/material/Link";
import Typography from "@mui/material/Typography";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import AddressForm from "./AddressForm";
import PaymentForm from "./PaymentForm";
import Review from "./Review";
import { API_ENDPOINT, BOOKING_API_ENDPOINT, PAYMENT_API_ENDPOINT } from "../../Data/API";
import { LinearProgress } from "@mui/material";
import Reciept from "./Reciept";

function loadScript(src) {
  return new Promise((resolve) => {
    const script = document.createElement("script");
    script.src = src;
    script.onload = () => {
      resolve(true);
    };
    script.onerror = () => {
      resolve(false);
    };
    document.body.appendChild(script);
  });
}

const steps = ["Shipping address", "Payment details", "Review your order"];

const theme = createTheme();

export default function Checkout(props) {
  const [activeStep, setActiveStep] = React.useState(0);
  const [isOrderConfirmed, setisOrderConfirmed] = useState(false);
  const [bookingId, setbookingId] = useState(null);
  const handleNext = (e) => {
    console.error(e.target.textContent);
    if (e.target.textContent === "Place order") {
      console.error("Place order clicked");
      displayRazorpay();
    }
    setActiveStep(activeStep + 1);
  };

  const handleBack = () => {
    setActiveStep(activeStep - 1);
  };

  function getStepContent(step) {
    switch (step) {
      case 0:
        return <AddressForm tId={props.tspId} />;
      case 1:
        return <PaymentForm />;
      case 2:
        return <Review />;
      default:
        throw new Error("Unknown step");
    }
  }

  async function displayRazorpay() {
    const res = await loadScript(
      "https://checkout.razorpay.com/v1/checkout.js"
    );

    if (!res) {
      alert("Razorpay SDK failed to load. Are you online?");
      return;
    }

    const resp = await fetch(BOOKING_API_ENDPOINT + "/create-booking", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: localStorage.getItem("customer"),
    }).then((t) => t.json());
    const data = resp.razorPay;
    setbookingId(resp.bookingId);

    const options = {
      key: "rzp_test_akArRL3ZRpmWSk",
      currency: "INR",
      // amount: 20000,
      amount: data.applicationFee,
      order_id: data.razorpayOrderId,
      name: "Speedy Go",
      description: "Thank you using our services",
      // image: 'http://localhost:1337/logo.svg',
      handler: async function (response) {
        // alert(response.razorpay_payment_id)
        // alert(response.razorpay_order_id)
        // alert(response.razorpay_signature)
        setTimeout(() => setisOrderConfirmed(true), 2000);

        const payload = {
          paymentId: response.razorpay_payment_id,
          orderId: response.razorpay_order_id,
          signature: response.razorpay_signature,
        };

        const data = await fetch(PAYMENT_API_ENDPOINT + "/complete-payment", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(payload),
        })
          .then((t) => {
            t.json();
          })
      },
    };
    const paymentObject = new window.Razorpay(options);
    paymentObject.open();
  }

  return (
    <ThemeProvider theme={theme}>
      <Container
        component="main"
        maxWidth="sm"
        sx={{ mb: 4 }}
        className="popup_container"
      >
        <Paper
          variant="outlined"
          sx={{ my: { xs: 3, md: 6 }, p: { xs: 2, md: 3 } }}
          className="paper"
        >
          <Typography component="h1" variant="h4" align="center">
            Create Booking!
          </Typography>
          <Stepper activeStep={activeStep} sx={{ pt: 3, pb: 5 }}>
            {steps.map((label) => (
              <Step key={label}>
                <StepLabel>{label}</StepLabel>
              </Step>
            ))}
          </Stepper>
          {activeStep === steps.length ? (
            <React.Fragment>
              {isOrderConfirmed ? (
                <>
                  <Typography variant="h5" gutterBottom>
                    Thank you for your order.
                  </Typography>
                  <Typography variant="subtitle1">
                    {bookingId ? (
                      <>
                        Your order number is
                        <strong> #{bookingId}</strong>
                        . We have emailed your order confirmation on your
                        registered email Id, and will send you an update when
                        your order has shipped.
                      </>
                    ) : (
                      "Error occured while placing the order..."
                    )}
                  </Typography>
                </>
              ) : (
                <Typography variant="h6" gutterBottom>
                  Getting confirmation from merchant....
                  <LinearProgress />
                </Typography>
              )}
            </React.Fragment>
          ) : (
            <React.Fragment>
              {getStepContent(activeStep)}
              <Box sx={{ display: "flex", justifyContent: "flex-end" }}>
                {activeStep !== 0 && (
                  <Button onClick={handleBack} sx={{ mt: 3, ml: 1 }}>
                    Back
                  </Button>
                )}

                <Button
                  variant="contained"
                  onClick={(e) => handleNext(e)}
                  sx={{ mt: 3, ml: 1 }}
                >
                  {activeStep === steps.length - 1 ? "Place order" : "Next"}
                </Button>
              </Box>
            </React.Fragment>
          )}
        </Paper>
      </Container>
    </ThemeProvider>
  );
}
