package com.example.madcamp_1st

import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.contacts.*

class ContactsActivity : AppCompatActivity() {

    var itemList = arrayListOf<Item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contacts)
        loadMyContacts()
        // set recycler view
        setRecyclerView()
    }

    private fun setRecyclerView() {
        val mAdapter = MainRvAdapter(this, itemList)
        recycler_view.adapter = mAdapter

        val layoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = layoutManager
        recycler_view.setHasFixedSize(true)
    }

    /**
     * loadMyContacts()
     *      load contacts from the phone
     */
    private fun loadMyContacts() {
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
                    val id = contactsPointer.getString(contactsPointer.getColumnIndex(
                        ContactsContract.Contacts._ID))
                    val name = contactsPointer.getString(contactsPointer.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME))
                    val phoneNumber = contactsPointer.getString(contactsPointer.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)).toInt()
                    // if he has phone number,
                    if (phoneNumber > 0) {
                        val phoneNumberPointer = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", arrayOf(id), null)

                        if (phoneNumberPointer == null) {
                            System.exit(0); // some errors
                        } else {
                            if (phoneNumberPointer.count > 0) {
                                while (phoneNumberPointer.moveToNext()) {
                                    val phoneNumValue = phoneNumberPointer.getString(phoneNumberPointer.getColumnIndex(
                                        ContactsContract.CommonDataKinds.Phone.NUMBER))
                                    val item: Item = Item(name, phoneNumValue)
                                    itemList.add(item)
                                }
                            }
                            phoneNumberPointer.close()
                        }
                    }
                    // Toast.makeText(this@MainActivity, "$id $name $phoneNumber", Toast.LENGTH_LONG).show()
                }
                // Toast.makeText(this@MainActivity, "read all the contacts", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@ContactsActivity, "No ContactsActivity in the phone", Toast.LENGTH_LONG).show()
            }
            contactsPointer.close()
        }
    }
}