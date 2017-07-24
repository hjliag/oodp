#contact.py

from sqlalchemy import create_engine, Column, Integer, String
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import scoped_session, sessionmaker

# mysql login information
USER = 'root'
PASSWORD = '1234'
HOST = 'localhost'
DATABASE = 'Contactdb'

# preparing sqlalchemy
engine = create_engine('mysql+mysqldb://%s:%s@%s' % (USER, PASSWORD, HOST), echo=True)
engine.execute("USE %s;" % DATABASE)

Base = declarative_base()

session = sessionmaker()
session.configure(bind=engine)
s = session()

# Contact class
class Contact(Base):
    __tablename__ = 'ContactTable'
    id = Column(Integer, primary_key=True)
    name = Column(String(250), nullable=False)
    number = Column(String(100), nullable=False)
    address = Column(String(250))
    email = Column(String(250))
    memo = Column(String(250))

# convert query to dictionary (contact query only)
def contact_query_to_dict(q):
    entries = [dict(id=contact.id, name=contact.name, number=contact.number,
    address=contact.address, email=contact.email, memo=contact.memo) for contact in q]
    return entries

# Create
def add_contact(_name, _number, _address, _email, _memo):
    new_contact = Contact(name=_name, number=_number, address=_address, email=_email, memo=_memo)
    s.add(new_contact)
    s.commit()

def add_contact(new_contact):
    s.add(new_contact)
    s.commit()

# Read
def get_contacts_all():
    q = s.query(Contact).order_by(Contact.name)
    return contact_query_to_dict(q)

def get_contacts_by_name(name):
    q = s.query(Contact).filter(Contact.name.like("%{}%".format(name))).order_by(Contact.name)
    return contact_query_to_dict(q)

def get_contacts_by_number(number):
    q = s.query(Contact).filter(Contact.number.like("%{}%".format(number))).order_by(Contact.name)
    return contact_query_to_dict(q)

def get_contacts_by_number_exact(number):
    q = s.query(Contact).filter(Contact.number==number)
    return contact_query_to_dict(q)

def get_contacts_by_id(id):
    q = s.query(Contact).filter(Contact.id==id)
    return contact_query_to_dict(q)

# Update
def update_contact(updated_contact):
    q = s.query(Contact).filter(Contact.id==updated_contact.id).first()
    q.name = updated_contact.name
    q.number = updated_contact.number
    q.address = updated_contact.address
    q.email = updated_contact.email
    q.memo = updated_contact.memo
    s.commit()

# Delete
def delete_contact_by_number(number):
    s.query(Contact).filter(Contact.number == number).delete()
    s.commit()
def delete_contact_by_name(name):
    s.query(Contact).filter(Contact.name == name).delete()
    s.commit()
def delete_contacty_by_id(id):
    s.query(Contact).filter(Contact.id==id).delete()
    s.commit()


## should run this file before running the server to create database
if __name__ == '__main__':

    Base.metadata.create_all(engine)

    contact1 = Contact(name='asdf', number='12345')
    print get_contacts_all()
