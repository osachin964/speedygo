import React, { useEffect, useState } from "react";
import Cards from "../Cards/Cards";
import Table from "../Table/Table";
import "./MainDash.css";
import { getCardsData } from "../../Data/Data";
import Button from "@mui/material/Button";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import Checkout from "../CheckoutComp/Checkout";
import {
  API_ENDPOINT,
  fetchBookingDetails,
  getAllTransporters,
} from "../../Data/API";
import GenQuote from "../GenerateQuote/GenQuote";
import { Box, TextField, Typography } from "@mui/material";
import TrackOrder from "../OrderTracker/TrackOrder";

const TrackBookingPage = (props) => {
  const [transporterDetails, settransporterDetails] = useState(null);
  const [bookingStatus, setbookingStatus] = useState(null);
  const map = new Map();

  map.set("Created", 1);
  map.set("Accepted", 2);
  map.set("In Transit", 3);
  map.set("Delivered", 4);
  map.set("Rejected", 10);
  map.set("Declined", 10);


  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);

    async function fetchData() {
      const response = await fetchBookingDetails(data.get("bookingId"));
      console.log("BOOKING STATUS IS ", response.status);
      setbookingStatus(response.status);
    }
    fetchData();
  };

  return props.role.toLowerCase() === "customer" ? (
    <div className="TrackBookingPage ">
      <div>
        <h1>Track Booking Status</h1>
        <>
          <Typography variant="h6">Track your booking...</Typography>
          <Box
            component="form"
            onSubmit={handleSubmit}
            noValidate
            sx={{ mt: 1 }}
          >
            <TextField
              margin="normal"
              required
              fullWidth
              id="bookingId"
              label="Tracking Id"
              name="bookingId"
              autoFocus
            />
            {bookingStatus ? (
              <>
                <Typography variant="h6">
                  Your current booking status is :
                </Typography>
                <TrackOrder activeStep={map.get(bookingStatus)} />
              </>
            ) : (
              ""
            )}

            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              Get Live Updates
            </Button>
          </Box>{" "}
        </>
      </div>
    </div>
  ) : (
    "Error while loading "
  );
};

export default TrackBookingPage;
