#sms.py

from sqlalchemy import create_engine, Column, Integer, String, Boolean, DateTime, func, desc
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import scoped_session, sessionmaker
import datetime

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

# sms class
class Sms(Base):
    __tablename__ = 'SmsTable'
    id = Column(Integer, primary_key=True)
    number = Column(String(100), nullable=False)
    message = Column(String(500), nullable=False)
    send_time = Column(DateTime, default=func.now())
    sent = Column(Boolean, default=True)

# convert query to dictionary (sms query only)
def sms_query_to_dict(q):
    entries = [dict(id=sms.id, number=sms.number, message=sms.message,
    send_time=sms.send_time, sent=sms.sent) for sms in q]
    return entries

# Create
def add_sms(new_sms):
    s.add(new_sms)
    s.commit()

def add_sms(_number, _message, _send_time, _sent):
    new_sms = Sms(number=_number, message=_message, send_time=_send_time, sent=_sent)
    s.add(new_sms)
    s.commit()

# Read
def get_sms_all():
    q = s.query(Sms).order_by(desc(Sms.send_time))
    return sms_query_to_dict(q)

def get_sms_by_number(number):
    q = s.query(Sms).filter(Sms.number.like("%{}%".format(number))).order_by(desc(Sms.send_time))
    return sms_query_to_dict(q)

def get_sms_by_message(message):
    q = s.query(Sms).filter(Sms.message.like("%{}%".format(message))).order_by(desc(Sms.send_time))
    return sms_query_to_dict(q)

# Delete
def delete_sms_by_number(number):
    s.query(Sms).filter(Sms.number==number).delete()
    s.commit()

def delete_sms_by_id(id):
    s.query(Sms).filter(Sms.id==id).delete()
    s.commit()

## should run this file before running the server to create database
if __name__ == '__main__':
    create_str = "CREATE DATABASE IF NOT EXISTS %s ;" % (DATABASE)
    engine.execute(create_str)
    engine.execute("USE %s;" % DATABASE)

    Base.metadata.create_all(engine)

    sms1 = Sms(number='12345', message='hello sms')
