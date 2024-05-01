import React, { useState, useEffect } from 'react';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';

export default function AddressForm() {

    const [quotation, setquotation] = useState(
        {
            totalPrice: "",
            dropAddress: "",
            dropLocation: "",
            pickupAddress: "",
            pickupLocation: "",
            serviceType: ""
        }
    );

    useEffect(() => {
        let savedQuotation = JSON.parse(localStorage.getItem("quotation"))
        if (savedQuotation)
            setquotation(savedQuotation)
    }, [])

    useEffect(() => {
        localStorage.setItem("quotation", JSON.stringify(quotation));
    }, [quotation])


    return (
        <React.Fragment>
            <Typography variant="h6" gutterBottom>
                Please provide address details below
            </Typography>
            <Grid container spacing={3}>
                <Grid item xs={12} sm={6}>
                    <TextField
                        required
                        id="address1"
                        name="address1"
                        label="Pickup Address"
                        fullWidth
                        value={quotation.pickupAddress}
                        autoComplete="shipping address-line1"
                        variant="standard"
                        onChange={(event) => setquotation((prevState) => ({ ...prevState, pickupAddress: event.target.value }))}

                    />
                </Grid>


                <Grid item xs={12} sm={6}>
                    <TextField
                        required
                        id="zip"
                        name="zip"
                        label="Zip / Postal code"
                        value={quotation.pickupLocation}
                        fullWidth
                        autoComplete="shipping postal-code"
                        variant="standard"
                        onChange={(event) => setquotation((prevState) => ({ ...prevState, pickupLocation: event.target.value }))}

                    />
                </Grid>
                <Grid item xs={12} sm={6}>
                    <TextField
                        required
                        id="address2"
                        name="address2"
                        label="Drop Address"
                        fullWidth
                        value={quotation.dropAddress}
                        variant="standard"
                        onChange={(event) => {
                            setquotation((prevState) => ({ ...prevState, dropAddress: event.target.value }))
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
                        value={quotation.dropLocation}
                        variant="standard"
                        onChange={(event) => {
                            setquotation((prevState) => ({ ...prevState, dropLocation: event.target.value }))
                        }}
                    />
                </Grid>
                {/* <Grid item xs={12}>
                    <FormControlLabel
                        control={<Checkbox color="secondary" name="saveAddress" value="yes" />}
                        label="Use this address for payment details"
                    />
                </Grid> */}
            </Grid>
        </React.Fragment>
    );
}