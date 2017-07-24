# project4.py

from flask import Flask, render_template, request, redirect, url_for
import api.call, api.contact, api.sms, datetime
app = Flask(__name__)

# home default
@app.route('/', methods = ['POST', 'GET'])
def home():
    if request.method == 'POST':
        pass
    return render_template("home.html")

# contact main page.
# include list of contacts
@app.route('/contact', methods = ['POST', 'GET'])
def contact_main():
    return render_template('contact_main.html', contacts=api.contact.get_contacts_all())

# call main page
# include list of call history
@app.route('/call', methods = ['POST', 'GET'])
def call_main():
    return render_template('call_main.html', calls=api.call.get_call_all())

# sms main page
# include list of sms
@app.route('/sms', methods = ['POST', 'GET'])
def sms_main():
    return render_template('sms_main.html', smss=api.sms.get_sms_all())

# contact add page
@app.route('/contact/add', methods=['POST','GET'])
def contact_add():
    if request.method == 'POST':
        result = request.form
        contact = api.contact.Contact(name=result['name'], number=result['number'],
        email=result['email'], address=result['address'], memo=result['memo'])
        api.contact.add_contact(contact)
        return redirect(url_for('contact_main'))
    return render_template('contact_add.html')

# sms add page
# lets the user simulate sending sms
@app.route('/sms/add', methods=['POST', 'GET'])
def sms_add():
    if request.method == 'POST':
        result = request.form
        api.sms.add_sms(_number=result['number'], _message=result['message'], _sent=True, _send_time=datetime.datetime.now())
        return redirect(url_for('sms_main'))
    return render_template('sms_add.html')

# call add page
# simulate calling
@app.route('/call/add', methods=['POST', 'GET'])
def call_add():
    if request.method == 'POST':
        result = request.form
        print result['number']
        start_time = datetime.datetime.strptime(result['start'], "%Y-%m-%d-%H-%M-%S")
        end_time = datetime.datetime.strptime(result['end'], "%Y-%m-%d-%H-%M-%S")
        api.call.add_call(_number=result['number'], _called=True, _missed=True, _start_time=start_time, _end_time=end_time)
        return redirect(url_for('call_main'))
    return render_template('call_add.html')

# contact editing page
@app.route('/contact/edit', methods=['POST', 'GET'])
def contact_edit():
    if request.method == 'GET':
        _edit_contact = api.contact.get_contacts_by_id(request.args.get('id'))
        return render_template('contact_edit.html', edit_contact=_edit_contact)
    if request.method == 'POST':
        result = request.form
        updated_contact = api.contact.Contact(id=result['id'], name=result['name'], number=result['number'],
        email=result['email'], address=result['address'], memo=result['memo'])
        api.contact.update_contact(updated_contact)
        return redirect(url_for('contact_main'))
    return 'why in edit???'

# url for deleting contact
@app.route('/contact/delete', methods=['POST', 'GET'])
def contact_delete():
    if request.method == 'GET':
        api.contact.delete_contacty_by_id(request.args.get('id'))
        return redirect(url_for('contact_main'))
    return 'why in here /contact/delete ???'

# url for deleting call
@app.route('/call/delete', methods=['POST', 'GET'])
def call_delete():
    if request.method == 'GET':
        api.call.delete_call_by_id(request.args.get('id'))
        return redirect(url_for('call_main'))
    return 'why in here /call/delete ???'

# url for deleting sms
@app.route('/sms/delete', methods=['POST', 'GET'])
def sms_delete():
    if request.method == 'GET':
        api.sms.delete_sms_by_id(request.args.get('id'))
        return redirect(url_for('sms_main'))
    return 'why in here /sms/delete ???'

if __name__ == "__main__":
    app.run(debug=True)
