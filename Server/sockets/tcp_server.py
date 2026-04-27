import socket

HOST = "100.118.95.40"
PORT = 5000

server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)

server_socket.bind((HOST, PORT))
server_socket.listen(1)

print("TCP server is waiting for a client...")

client_socket, client_address = server_socket.accept()
print("Client connected:", client_address)

reader = client_socket.makefile("r", encoding="utf-8")
writer = client_socket.makefile("w", encoding="utf-8")

while True:
    client_message = reader.readline()

    if client_message == "":
        print("Client disconnected.")
        break

    client_message = client_message.strip()
    print("Client:", client_message)

    if client_message.lower() == "exit":
        print("Chat ended by client.")
        break

    server_message = input("Server: ")

    writer.write(server_message + "\n")
    writer.flush()

    if server_message.lower() == "exit":
        print("Chat ended by server.")
        break

reader.close()
writer.close()
client_socket.close()
server_socket.close()

print("TCP server closed.")