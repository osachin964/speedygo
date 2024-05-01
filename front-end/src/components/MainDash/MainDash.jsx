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

const MainDash = (props) => {
  const [open, setopen] = useState(false);
  const [transporterDetails, settransporterDetails] = useState(null);

  useEffect(() => {
    async function fetchData() {
      const response = await getAllTransporters();
      const data = response;
      // console.log("fetched transporters ", data)
      settransporterDetails(data);
    }
    fetchData();
  }, []);

  const handleClose = () => {
    setopen(false);
  };

  const openForm = () => {
    localStorage.setItem("customer", null);
    setopen(true);
  };

  return props.role.toLowerCase() === "customer" ? (
    <div className="MainDash">
      <div className="topLine">
        <h1>{props.role} Dashboard</h1>
        <Button variant="outlined" onClick={openForm}>
          Generate Quotation
        </Button>
        <Dialog
          open={open}
          onClose={handleClose}
          fullWidth={true}
          className="dialog_class"
        >
          <DialogContent className="css-tlc64q-MuiPaper-root-MuiDialog-paper">
            <GenQuote />
          </DialogContent>
        </Dialog>
      </div>
      {transporterDetails ? (
        <>
          <Cards
            cardsData={transporterDetails.slice(0, 3)}
            changeTsp={props.setselectedTransporter}
          />
          <Cards
            cardsData={transporterDetails.slice(3, 6)}
            changeTsp={props.setselectedTransporter}
          />
          <Cards
            cardsData={transporterDetails.slice(6)}
            changeTsp={props.setselectedTransporter}
          />
        </>
      ) : (
        ""
      )}
    </div>
  ) : (
    <div className="MainTable">
      <h1>{props.role} Dashboard</h1>
      <Table />
    </div>
  );
};

export default MainDash;
