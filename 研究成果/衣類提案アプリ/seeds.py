from app import app, db
from models.item import Item

def item_seed():
  with app.app_context():
    item = Item(name='hoge')

    db.session.add(item)
    db.session.commit()

if __name__ == '__main__':
  item_seed()