import React, { useState, useEffect } from 'react';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import { Button } from '@mui/material';

export default function AddressForm(props) {

// console.log("my pros --",props)
    const [customer, setCustomer] = useState(
        {
        totalPrice: "",
        customerName: JSON.parse(localStorage.getItem("user")).name,
        customerId: JSON.parse(localStorage.getItem("user")).id,
        customerMobileNo: "",
        dropAddress: "",
        dropLocation: "",
        pickupAddress: "",
        pickupLocation: "",
        transporterId: props.tId,
        transporterName: "",
        serviceType: ""
    }
    );
    
    useEffect(()=>{
        let savedCust = JSON.parse(localStorage.getItem("customer"))
        if(savedCust)
        setCustomer(savedCust)
    },[])

    useEffect(() => {
        localStorage.setItem("customer", JSON.stringify(customer));
    }, [customer])


    return (
        <React.Fragment>
            <Typography variant="h6" gutterBottom>
                Booking details
            </Typography>
            <Grid container spacing={3}>
                <Grid item xs={12} sm={6}>
                    <TextField
                        required
                        id="firstName"
                        name="firstName"
                        label="Full Name"
                        value={customer.customerName}
                        fullWidth
                        autoComplete="given-name"
                        variant="standard"
                        onChange={(event) => {
                            setCustomer((prevState) => ({ ...prevState, customerName: event.target.value }))
                            // setCustomer((prevState) => ({ ...prevState, customerId: event.target.value.toLowerCase() + "@gmail.com" }))
                        }}
                    />
                </Grid>
                <Grid item xs={12} sm={6}>
                    <TextField
                        id="state"
                        name="state"
                        label="Mobile Number"
                        value={customer.customerMobileNo}
                        fullWidth
                        variant="standard"
                        onChange={(event) => setCustomer((prevState) => ({ ...prevState, customerMobileNo: event.target.value }))}
                    />
                </Grid>

                <Grid item xs={12} sm={6}>
                    <TextField
                        required
                        id="address1"
                        name="address1"
                        label="Pickup Address"
                        fullWidth
                        value={customer.pickupAddress}
                        autoComplete="shipping address-line1"
                        variant="standard"
                        onChange={(event) => setCustomer((prevState) => ({ ...prevState, pickupAddress: event.target.value }))}

                    />
                </Grid>


                <Grid item xs={12} sm={6}>
                    <TextField
                        required
                        id="zip"
                        name="zip"
                        label="Zip / Postal code"
                        value={customer.pickupLocation}
                        fullWidth
                        autoComplete="shipping postal-code"
                        variant="standard"
                        onChange={(event) => setCustomer((prevState) => ({ ...prevState, pickupLocation: event.target.value }))}

                    />
                </Grid>
                <Grid item xs={12} sm={6}>
                    <TextField
                        required
                        id="address2"
                        name="address2"
                        label="Drop Address"
                        fullWidth
                        value={customer.dropAddress}
                        variant="standard"
                        onChange={(event) => {
                            setCustomer((prevState) => ({ ...prevState, dropAddress: event.target.value }))
                        }}
                    />
                </Grid>


                <Grid item xs={12} sm={6}>
                    <TextField
                        required
                        id="zip"
                        name="zip"
                        label="Zip / Postal code"
                        fullWidth
                        value={customer.dropLocation}
                        variant="standard"
                        onChange={(event) => {
                            setCustomer((prevState) => ({ ...prevState, dropLocation: event.target.value }))
                        }}
                    />
                </Grid>

                <Grid item xs={12} sm={6}>
                    <TextField
                        required
                        id="transporter"
                        name="transporter"
                        label="Transporter"
                        fullWidth
                        disabled={true}
                        value={
                            customer.transporterId
                        }
                        variant="standard"
                   
                    />
                </Grid>

                <Grid item xs={12} sm={6}>
                    <TextField
                        required
                        id="pick_up"
                        name="pick_up"
                        label="Service Type"
                        fullWidth
                        value={customer.serviceType}
                        variant="standard"
                        onChange={(event) => {
                            setCustomer((prevState) => ({ ...prevState, serviceType: event.target.value }))
                        }}
                    />
                </Grid>



            </Grid>

        </React.Fragment>
    );
}
