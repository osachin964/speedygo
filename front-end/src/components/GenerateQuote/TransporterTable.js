import React, { useState, useEffect } from "react";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import Switch from "@mui/material/Switch";
import "../Table/Table.css";
import { VendorBookings } from "../../Data/Data";
import { fetchPriceApi, generateQuoteApi } from "../../Data/API";

import Typography from "@mui/material/Typography";
import LinearProgress from "@mui/material/LinearProgress";

export default function TransporterTable() {
  const [data, setData] = useState(null);

  useEffect(() => {
    async function fetchData() {
      const response = await generateQuoteApi();
      const data = response;
      // console.log("fetched data ", data)
      setData(data);
    }
    fetchData();
  }, []);

  return (
    <React.Fragment>
      {data ? (
        <div className="Table">
          <h3>Best Transporters near you.</h3>
          <TableContainer
            component={Paper}
            style={{ boxShadow: "0px 13px 20px 0px #80808029" }}
          >
            <Table sx={{ minWidth: 650 }} aria-label="simple table">
              <TableHead>
                <TableRow>
                  <TableCell>Company Name</TableCell>
                  <TableCell align="left">Experience</TableCell>
                  <TableCell align="left">Estimated Price</TableCell>
                  <TableCell align="left">Office Address</TableCell>
                  <TableCell align="left">Services Offered</TableCell>
                  <TableCell align="left">Ratings</TableCell>
                </TableRow>
              </TableHead>
              <TableBody style={{ color: "white" }}>
                {data.map((row) => (
                  <TableRow
                    key={row.companyName}
                    sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
                  >
                    <TableCell component="th" scope="row">
                      {row.companyName}
                    </TableCell>
                    <TableCell align="left">
                      {row.experience + "Years"}
                    </TableCell>
                    <TableCell align="left">
                      {row.calculatedPrice.toLocaleString("en-US", {
                        style: "currency",
                        currency: "INR",
                      })}
                    </TableCell>
                    <TableCell align="left">{row.officeAddress}</TableCell>
                    <TableCell align="left">
                      {row.serviceType.join(", ")}
                    </TableCell>
                    <TableCell align="left">
                      {row.ratings ? row.ratings : "NA"}
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        </div>
      ) : (
        <Typography variant="h6" gutterBottom>
          Finding best transporters near you...
          <LinearProgress />
        </Typography>
      )}
    </React.Fragment>
  );
}
