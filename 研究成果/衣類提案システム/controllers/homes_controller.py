from flask import Blueprint, render_template
homes_controller = Blueprint("homes_controller", __name__)

@homes_controller.route("/")
def top():
  return render_template("top.html")