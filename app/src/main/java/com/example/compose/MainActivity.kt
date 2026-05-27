package com.example.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.ui.theme.ComposeTheme

data class Contact(
    val name: String,
    val surname: String? = null,
    val familyName: String,
    val imageRes: Int? = null,
    val isFavorite: Boolean = false,
    val phone: String,
    val address: String,
    val email: String? = null,
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeTheme {
                ContactsScreen(contact = favoriteContactWithoutPhoto)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ContactsScreen(contact: Contact) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6200EE),
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        ContactDetails(
            contact = contact,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun ContactDetails(contact: Contact, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.padding(top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ContactAvatar(contact = contact)

            Spacer(modifier = Modifier.height(20.dp))

            ContactName(contact = contact)
        }

        Spacer(modifier = Modifier.height(52.dp))

        Column(
            modifier = Modifier.widthIn(max = 300.dp),
            verticalArrangement = Arrangement.spacedBy(22.dp)
        ) {
            InfoRow(label = stringResource(R.string.phone), value = contact.phone)
            InfoRow(label = stringResource(R.string.address), value = contact.address)
            contact.email?.let { email ->
                InfoRow(label = stringResource(R.string.email), value = email)
            }
        }
    }
}

@Composable
private fun ContactAvatar(contact: Contact) {
    if (contact.imageRes != null) {
        Image(
            painter = painterResource(contact.imageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(96.dp)
                .height(64.dp)
        )
    } else {
        Box(
            modifier = Modifier.size(56.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.circle),
                contentDescription = null,
                tint = Color(0xFFD8D8D8),
                modifier = Modifier.fillMaxSize()
            )
            Text(
                text = contact.initials,
                color = Color(0xFF333333),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun ContactName(contact: Contact) {
    Text(
        text = contact.nameWithSurname,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = contact.familyName,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        if (contact.isFavorite) {
            Icon(
                painter = painterResource(android.R.drawable.star_big_on),
                contentDescription = null,
                tint = Color(0xFFFF9800),
                modifier = Modifier
                    .padding(start = 6.dp)
                    .size(18.dp)
            )
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.widthIn(max = 300.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "$label:",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.End,
            modifier = Modifier.width(90.dp)
        )

        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 8.dp)
                .width(190.dp)
        )
    }
}

private val Contact.nameWithSurname: String
    get() = listOfNotNull(name, surname).joinToString(" ")

private val Contact.initials: String
    get() = name.take(1) + familyName.take(1)

private val favoriteContactWithoutPhoto = Contact(
    name = "Евгений",
    surname = "Андреевич",
    familyName = "Лукашин",
    isFavorite = true,
    phone = "+7 495 495 95 95",
    address = "г. Москва, 3-я улица\nСтроителей, д. 25, кв. 12",
    email = "ELukashin@practicum.ru"
)

private val contactWithPhotoWithoutEmail = Contact(
    name = "Василий",
    familyName = "Кузякин",
    imageRes = R.drawable.contact_photo,
    phone = "---",
    address = "Ивановская область, дер.\nКрутово, д. 4"
)

@Preview(showBackground = true, name = "ProfileWithoutPhotoPreview")
@Composable
fun FavoriteContactWithoutPhotoPreview() {
    ComposeTheme {
        ContactsScreen(contact = favoriteContactWithoutPhoto)
    }
}

@Preview(showBackground = true, name = "ProfileWithPhotoPreview")
@Composable
fun ContactWithPhotoWithoutEmailPreview() {
    ComposeTheme {
        ContactsScreen(contact = contactWithPhotoWithoutEmail)
    }
}