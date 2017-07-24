#call.py

from sqlalchemy import create_engine, Column, Integer, Boolean, String, DateTime, func, desc
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

# call class
class Call(Base):
    __tablename__ = 'CallTable'
    id = Column(Integer, primary_key=True)
    number = Column(String(100), nullable=False)
    called = Column(Boolean, default=True)
    missed = Column(Boolean, default=True)
    start_time = Column(DateTime, default=func.now())
    end_time = Column(DateTime)

# convert query to dictionary (call query only)
def call_query_to_dict(q):
    entries = [dict(id=call.id, number=call.number, called=call.called,
    missed=call.missed, start_time=call.start_time, end_time=call.end_time ) for call in q]
    return entries


# Create
# add new call entry using Call object
def add_call(new_call):
    s.add(new_call)
    s.commit()
# add new call entry using the attributes
def add_call(_number, _called, _missed, _start_time, _end_time):
    new_call = Call(number=_number, called=_called, missed=_missed, start_time=_start_time, end_time=_end_time)
    s.add(new_call)
    s.commit()


# Read
# get all entries in dictionary
def get_call_all():
    q = s.query(Call).order_by(desc(Call.start_time))
    return call_query_to_dict(q)
# get all entries including the argument "number"
def get_call_by_number(number):
    q = s.query(Call).filter(Call.number.like("%{}%".format(number))).order_by(desc(Call.start_time))
    return call_query_to_dict(number)


# Delete
# delete entries using number
def delete_call_by_number(number):
    s.query(Call).filter(Call.number==number).delete()
    s.commit()
# delete entries using id
def delete_call_by_id(id):
    s.query(Call).filter(Call.id==id).delete()
    s.commit()


## should run this file before running the server to create database 
if __name__ == '__main__':
    create_str = "CREATE DATABASE IF NOT EXISTS %s ;" % (DATABASE)
    engine.execute(create_str)
    engine.execute("USE %s;" % DATABASE)

    Base.metadata.create_all(engine)

    call1 = Call(number='12345')
