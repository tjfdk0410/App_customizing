package com.example.madcamp_1st

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import android.Manifest
import android.content.pm.PackageManager
import android.provider.ContactsContract
import androidx.core.app.ActivityCompat
import java.lang.StringBuilder
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var toolbar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.title = "Contacts"
        // set bottom navigation bar
        setBottomNavBar()
        // 기능
        setPermissions()
        loadMyContacts()
    }

    /**
     *  setBottomNavBar()
     *      configure views for navigation bar
     */
    private fun setBottomNavBar() {
        toolbar = supportActionBar!!
        // listen selection
        bottom_navigation.setOnNavigationItemSelectedListener {
                item ->
            when (item.itemId) {
                R.id.navigation_contacts -> {
                    val fragment = ContactFragment()
                    toolbar.title = "Contacts"
                    supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
                }
                R.id.navigation_gallery -> {
                    val fragment = GalleryFragment()
                    toolbar.title = "Gallery"
                    supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
                }
                R.id.navigation_custom -> {
                    val fragment = CustomFragment()
                    toolbar.title = "Custom"
                    supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
                }
            }
            false
        }
    }
    /**
     * setPermissions()
     *      setting permissions on contacts
     */
    private fun setPermissions() =
        if (ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            // manually defined but don't know
            val MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1
            if (ActivityCompat.shouldShowRequestPermissionRationale(this@MainActivity, Manifest.permission.READ_CONTACTS)) {
                // description here if needed.
            } else {
                // No explanation needed, we can request the permission.
                // TODO
                ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.READ_CONTACTS), MY_PERMISSIONS_REQUEST_READ_CONTACTS)
            }
        } else {
            // Permission has already been granted
        }

    /**
     * loadMyContacts()
     *      load contacts from the phone
     */
    private fun loadMyContacts() {
        val builder = StringBuilder()
        // set pointer for reading contacts
        val contactsPointer = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)
        // if some errors occur (manage null pointer exception)
        if (contactsPointer == null) {
            System.exit(0); // some errors
        } else {    // no errors below
            // if there's any contacts
            if (contactsPointer.count > 0) {
                // till the end
                while (contactsPointer.moveToNext()) {
                    val id = contactsPointer.getString(contactsPointer.getColumnIndex(ContactsContract.Contacts._ID))
                    val name = contactsPointer.getString(contactsPointer.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    val phoneNumber = contactsPointer.getString(contactsPointer.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)).toInt()
                    // if he has phone number,
                    if (phoneNumber > 0) {
                        val phoneNumberPointer = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", arrayOf(id), null)

                        if (phoneNumberPointer == null) {
                            System.exit(0); // some errors
                        } else {
                            if (phoneNumberPointer.count > 0) {
                                while (phoneNumberPointer.moveToNext()) {
                                    val phoneNumValue = phoneNumberPointer.getString(phoneNumberPointer.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                                    builder.append("Contact:").append(name).append(", Phone Number: ").append(phoneNumValue).append("\n\n")
                                }
                            }
                            phoneNumberPointer.close()
                        }
                    }
                    // Toast.makeText(this@MainActivity, "$id $name $phoneNumber", Toast.LENGTH_LONG).show()
                }
                // Toast.makeText(this@MainActivity, "read all the contacts", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@MainActivity, "No Contacts in the phone", Toast.LENGTH_LONG).show()
            }
            contactsPointer.close()
        }
    }
}
