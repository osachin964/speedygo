import React, { useEffect, useState } from "react";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import Switch from "@mui/material/Switch";
import "./Table.css";
import "./spaces.css";
import {
  fetchBookingsForTransporter,
  generateQuoteApi,
  updateBookingStatus,
} from "../../Data/API";
import { Button, Dialog, DialogContent, Grid, Typography } from "@mui/material";
import Select from "@mui/material/Select";
import MenuItem from "@mui/material/MenuItem";

const makeStyle = (status) => {
  if (status === "Delivered") {
    return {
      background: "rgb(145 254 159 / 47%)",
      color: "green",
    };
  } else if (status === "Declined") {
    return {
      background: "#ffadad8f",
      color: "red",
    };
  } else if (status === "In Transit") {
    return {
      background: "#59bfff",
      color: "white",
    };
  } else if (status === "Accepted") {
    return {
      background: "#59bfff",
      color: "white",
    };
  } else
    return {
      background: "#59bfff",
      color: "white",
    };
};

export default function BasicTable() {
  const [data, setData] = useState(null);
  const [reload, setreload] = useState(false);

  // useEffect(() => {
  //   getBookingsByApiCall();
  // }, []);

  useEffect(() => {
    getBookingsByApiCall();
  }, [reload]);

  const handleChange = (event, row) => {
    // setstatus(event.target.value);
    console.error("kkkk", row.bookingId, event.target.value);
    async function fetchData(event, row) {
      console.error("LLLL", row.bookingId, event.target.value);

      const response = await updateBookingStatus(
        row.bookingId,
        event.target.value
      );
      setreload((rl) => !rl);
    }
    fetchData(event, row);
  };

  function getBookingsByApiCall() {
    async function fetchData() {
      const response = await fetchBookingsForTransporter();
      const data = response;
      console.error("fetched tsp details ", data);
      setData(data);
    }
    fetchData();
  }
  return data ? (
    <div className="Table Table_margin">
      <h3>Recent Bookings</h3>
      <TableContainer
        component={Paper}
        style={{ boxShadow: "0px 13px 20px 0px #80808029" }}
      >
        <Table sx={{ minWidth: 650 }} aria-label="simple table">
          <TableHead>
            <TableRow>
              <TableCell>BookingId</TableCell>
              <TableCell align="left">Customer Name</TableCell>
              <TableCell align="left">From</TableCell>
              <TableCell align="left">To</TableCell>
              <TableCell align="left">Service Type</TableCell>
              <TableCell align="left">Status</TableCell>
            </TableRow>
          </TableHead>
          <TableBody style={{ color: "white" }}>
            {data.map((row) => (
              <TableRow
                key={row.bookingId}
                sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
              >
                <TableCell component="th" scope="row">
                  {row.bookingId}
                </TableCell>
                <TableCell align="left">{row.customerName}</TableCell>
                <TableCell align="left">
                  {row.pickupAddress
                    ? row.pickupAddress + ", " + row.pickupLocation
                    : row.pickupLocation}
                </TableCell>
                <TableCell align="left">
                  {row.dropAddress
                    ? row.dropAddress + ", " + row.dropLocation
                    : row.dropLocation}
                </TableCell>
                <TableCell align="left">{row.serviceType}</TableCell>
                {/* <TableCell align="left">
                  <span className="status" style={makeStyle(row.status)}>
                    {row.status}
                  </span>
                </TableCell> */}
                <TableCell align="left">
                  <Select
                    value={row.status}
                    label={row.status}
                    onChange={(event) => handleChange(event, row)}
                  >
                    <MenuItem value="Created">Created</MenuItem>
                    <MenuItem value="Accepted">Accepted</MenuItem>
                    <MenuItem value="Declined">Declined</MenuItem>
                    <MenuItem value="Delivered">Delivered</MenuItem>
                    <MenuItem value="In Transit">In Transit</MenuItem>
                  </Select>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </div>
  ) : (
    <div>Loading table</div>
  );
}
