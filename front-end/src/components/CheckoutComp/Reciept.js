import * as React from 'react';
import Typography from '@mui/material/Typography';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemText from '@mui/material/ListItemText';
import Grid from '@mui/material/Grid';



const addresses = ['1 MUI Drive', 'Reactville', 'Anytown', '99999', 'USA'];
const payments = [
  { name: 'Payment Provider', detail: 'Razorpay' },
];

export default function Reciept() {
    let payload = JSON.parse(localStorage.getItem("customer"));
    const products = [
        {
            id:"123",
          name: payload.serviceType,
          desc: 'SpeedyGo super express',
          price:  payload.totalPrice +".00₹",
        },
      
        { name: 'Packaging', desc: '', price: '49.99₹' },
        { name: 'Discount', desc: '', price: '-49.99₹' },
      ];


  return (
    <React.Fragment>
      <Typography variant="h2" gutterBottom>
      </Typography>
      <Typography variant="h5" gutterBottom>
        Receipt Details
      </Typography>
      <List disablePadding>
        {products.map((product) => (
          <ListItem key={product.id} sx={{ py: 1, px: 0 }}>
            <ListItemText primary={product.name} secondary={product.desc} />
            <Typography variant="body2">{product.price}</Typography>
          </ListItem>
        ))}

        <ListItem sx={{ py: 1, px: 0 }}>
          <ListItemText primary="Total" />
          <Typography variant="subtitle1" sx={{ fontWeight: 700 }}>
            {(parseInt(payload.totalPrice)) + "₹" }
          </Typography>
        </ListItem>
      </List>
      <Grid container spacing={2}>
        <Grid item xs={12} sm={6}>
          <Typography variant="h6" gutterBottom sx={{ mt: 2 }}>
            Shipping
          </Typography>
          <Typography gutterBottom>{payload.customerName}</Typography>
          <Typography gutterBottom>{payload.dropAddress}</Typography>
        </Grid>
        <Grid item container direction="column" xs={12} sm={6}>
          <Typography variant="h6" gutterBottom sx={{ mt: 2 }}>
            Payment details
          </Typography>
          <Grid container>
            {payments.map((payment) => (
              <React.Fragment key={payment.name}>
                <Grid item xs={6}>
                  <Typography gutterBottom>{payment.name}</Typography>
                </Grid>
                <Grid item xs={6}>
                  <Typography gutterBottom>{payment.detail}</Typography>
                </Grid>
              </React.Fragment>
            ))}
          </Grid>
        </Grid>
      </Grid>
    </React.Fragment>
  );
}
