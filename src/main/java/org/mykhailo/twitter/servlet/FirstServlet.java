package org.mykhailo.twitter.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mykhailo.twitter.entity.Entity;
import org.mykhailo.twitter.entity.Ticket;
import org.mykhailo.twitter.repositoryMAP.TicketRepositoryMAP;

import java.io.IOException;

@WebServlet(name = "first_servlet", urlPatterns = {"/tickets"})
public class FirstServlet extends HttpServlet {

    private final TicketRepositoryMAP ticketRepositoryMAP = TicketRepositoryMAP.getINSTANCE();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");
        String idParam = req.getParameter("id");

        if (idParam == null || idParam.trim().isEmpty()) {
            resp.getWriter().write("{\"error\": \"Missing ID parameter\"}");
            return;
        }

        try {
            Long id = Long.parseLong(idParam);
            Entity<Long> ticket = ticketRepositoryMAP.getById(id);

            if (ticket != null) {
                resp.getWriter().write(ticket.toString());
            } else {
                resp.getWriter().write("{\"error\": \"Ticket not found\"}");
            }
        } catch (Exception e) {
            resp.getWriter().write("{\"error\": \"Invalid ID format or server error\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");

        String idParam = req.getParameter("id");
        String priceParam = req.getParameter("price");
        String trainIdParam = req.getParameter("trainId");

        if (idParam == null || priceParam == null || trainIdParam == null) {
            resp.getWriter().write("{\"error\": \"Missing fields: id, price, and trainId are required!\"}");
            return;
        }

        try {
            Long id = Long.parseLong(idParam);
            Long price = Long.parseLong(priceParam);
            Long trainId = Long.parseLong(trainIdParam);

            if (ticketRepositoryMAP.getById(id) != null) {
                resp.getWriter().write("{\"error\": \"Ticket with this ID already exists!\"}");
                return;
            }

            Ticket newTicket = new Ticket(id, price, trainId);
            ticketRepositoryMAP.save(newTicket);

            resp.getWriter().write("{\"success\": \"Ticket created\", \"data\": " + newTicket.toString() + "}");

        } catch (Exception e) {
            resp.getWriter().write("{\"error\": \"Server error during creation\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");

        String idParam = req.getParameter("id");
        String priceParam = req.getParameter("price");
        String trainIdParam = req.getParameter("trainId");

        if (idParam == null) {
            resp.getWriter().write("{\"error\": \"Missing ticket ID for update!\"}");
            return;
        }

        try {
            Long id = Long.parseLong(idParam);
            Ticket ticket = (Ticket) ticketRepositoryMAP.getById(id);

            if (ticket == null) {
                resp.getWriter().write("{\"error\": \"Ticket not found to update!\"}");
                return;
            }

            if (priceParam != null) {
                ticket.setPrice(Long.parseLong(priceParam));
            }
            if (trainIdParam != null) {
                ticket.setTrainId(Long.parseLong(trainIdParam));
            }

            resp.getWriter().write("{\"success\": \"Ticket updated\", \"data\": " + ticket.toString() + "}");

        } catch (Exception e) {
            resp.getWriter().write("{\"error\": \"Server error during update\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");
        String idParam = req.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            resp.getWriter().write("{\"error\": \"Missing ID parameter\"}");
            return;
        }

        try {
            Long id = Long.parseLong(idParam);

            boolean isDeleted = ticketRepositoryMAP.deleteById(id);

            if (isDeleted) {
                resp.getWriter().write("{\"success\": \"Ticket with ID " + id + " deleted!\"}");
            } else {
                resp.getWriter().write("{\"error\": \"Ticket not found!\"}");
            }
        } catch (Exception e) {
            resp.getWriter().write("{\"error\": \"Server error during delete\"}");
        }
    }
}